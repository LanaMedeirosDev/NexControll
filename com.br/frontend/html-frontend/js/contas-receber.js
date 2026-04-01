document.addEventListener('DOMContentLoaded', () => {
    // Preenche as datas com a data atual (padrão YYYY-MM-DD)
    const inputDataInicio = document.getElementById('dataInicio');
    const inputDataFim = document.getElementById('dataFim');
    
    if (inputDataInicio && inputDataFim) {
        const hoje = new Date();
        const ano = hoje.getFullYear();
        const mes = String(hoje.getMonth() + 1).padStart(2, '0');
        const dia = String(hoje.getDate()).padStart(2, '0');
        const dataPadrao = `${ano}-${mes}-${dia}`;
        
        inputDataInicio.value = dataPadrao;
        inputDataFim.value = dataPadrao;
    }

    // 1. Opcional - Forçar a aba "Contas a Receber" a ficar ativa (caso a navegação não tenha feito)
    const contasHoverLink = document.querySelector('[data-page="contas-receber"]');
    if (contasHoverLink && !contasHoverLink.classList.contains('active')) {
        document.querySelectorAll('.menu-link, .submenu-link').forEach(l => l.classList.remove('active'));
        contasHoverLink.classList.add('active');
        const parentSubmenu = contasHoverLink.closest('.has-submenu');
        if (parentSubmenu) {
            parentSubmenu.classList.add('open');
        }
    }

    // 2. Elementos da Tabela
    const tableBody = document.querySelector('#tabela-receitas tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    // 3. Função para Buscar Receitas (API)
    async function fetchReceitas() {
        try {
            // Requisição GET para /receitas
            const response = await fetch('/receitas');
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const data = await response.json();
            renderTable(data);
        } catch (error) {
            console.error('Erro ao buscar receitas:', error);
            
            // Tratamento de falha de conexão (API fora do ar)
            loadingMessage.style.display = 'none';
            // errorMessage.style.display = 'flex'; // Exibe mensagem de erro

            // Para que o usuário possa VIZUALIZAR a interface mesmo se a API estiver offline ainda:
            // Iremos renderizar dados mockados em caso de falha, para aprovação visual.
            console.warn('API /receitas inacessível. Usando dados mockados para visualização.');
            const mockData = [
                { id: 1, descricao: 'Desenvolvimento de Site para Cliente A', valor: 3500.00, vencimento: '2026-03-20', status: 'PENDENTE' },
                { id: 2, descricao: 'Manutenção Mensal Empresa B', valor: 500.00, vencimento: '2026-03-15', status: 'ATRASADO' },
                { id: 3, descricao: 'Consultoria de Marketing', valor: 1200.00, vencimento: '2026-03-10', status: 'RECEBIDO' },
                { id: 4, descricao: 'Venda de Licença (Plano Pro)', valor: 850.00, vencimento: '2026-03-25', status: 'PENDENTE' },
                { id: 5, descricao: 'Auditoria de Sistemas', valor: 4500.00, vencimento: '2026-04-10', status: 'PENDENTE' }
            ];
            renderTable(mockData);
        }
    }

    // 4. Formatação de Moeda
    function formatCurrency(value) {
        return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
    }

    // 5. Formatação de Data
    function formatDate(dateString) {
        // Exemplo: '2026-03-20T00:00:00' ou '2026-03-20'
        const baseDate = dateString.split('T')[0];
        const parts = baseDate.split('-');
        if (parts.length === 3) {
            return `${parts[2]}/${parts[1]}/${parts[0]}`;
        }
        return dateString;
    }

    // 6. Renderizar Dados na Tabela e Atualizar Resumo
    function renderTable(data) {
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        let total = 0;
        let recebido = 0;
        let pendente = 0;
        let atrasado = 0;

        if (!data || data.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5" style="text-align: center; padding: 3rem; color: var(--text-muted);">
                        Nenhuma conta a receber encontrada.
                    </td>
                </tr>`;
            updateCards(0, 0, 0, 0);
            return;
        }

        data.forEach(item => {
            const tr = document.createElement('tr');
            
            let statusClass = '';
            let statusText = (item.status || 'PENDENTE').toUpperCase();
            
            total += item.valor || 0;

            // Mapeamento visual para status
            if (statusText === 'PENDENTE' || statusText === 'PENDING') {
                statusClass = 'status-pendente';
                statusText = 'Pendente';
                pendente += item.valor || 0;
            } else if (statusText === 'RECEBIDO' || statusText === 'PAID') {
                statusClass = 'status-recebido';
                statusText = 'Recebido';
                recebido += item.valor || 0;
            } else if (statusText === 'ATRASADO' || statusText === 'LATE' || statusText === 'OVERDUE') {
                statusClass = 'status-atrasado';
                statusText = 'Atrasado';
                atrasado += item.valor || 0;
            } else {
                statusClass = 'status-pendente';
                statusText = 'Pendente';
                pendente += item.valor || 0;
            }

            tr.innerHTML = `
                <td>
                    <div style="font-weight: 500; color: var(--text-main); margin-bottom: 2px;">${item.descricao}</div>
                    <div style="font-size: 0.8rem; color: var(--text-muted);">Cód: #${item.id}</div>
                </td>
                <td style="font-weight: 600; color: var(--text-main);">${formatCurrency(item.valor)}</td>
                <td>${formatDate(item.vencimento)}</td>
                <td><span class="status-badge ${statusClass}">${statusText}</span></td>
                <td class="actions-col">
                    <div class="action-buttons">
                        <button class="btn-icon btn-receive" title="Receber Conta" data-action="receive" data-id="${item.id}">
                            <i data-lucide="check-circle"></i>
                        </button>
                        <button class="btn-icon btn-edit" title="Editar Conta" data-action="edit" data-id="${item.id}">
                            <i data-lucide="edit-2"></i>
                        </button>
                        <button class="btn-icon btn-delete" title="Excluir Conta" data-action="delete" data-id="${item.id}">
                            <i data-lucide="trash-2"></i>
                        </button>
                    </div>
                </td>
            `;
            tableBody.appendChild(tr);
        });

        updateCards(total, recebido, pendente, atrasado);

        // Atualiza os ícones Lucide para os botões novos gerados
        if (window.lucide) {
            window.lucide.createIcons();
        }
    }

    // 6.5. Atualiza os cards de resumo e gráfico
    function updateCards(total, recebido, pendente, atrasado) {
        document.getElementById('resumo-total').textContent = formatCurrency(total);
        document.getElementById('resumo-recebido').textContent = formatCurrency(recebido);
        document.getElementById('resumo-pendente').textContent = formatCurrency(pendente);
        document.getElementById('resumo-atrasado').textContent = formatCurrency(atrasado);

        const percEl = document.getElementById('chart-percent');
        if (total > 0) {
            const perc = ((recebido / total) * 100).toFixed(0);
            percEl.textContent = perc + '%';
        } else {
            percEl.textContent = '0%';
        }
    }

    // 7. Disparar Busca Inicial
    fetchReceitas();
});

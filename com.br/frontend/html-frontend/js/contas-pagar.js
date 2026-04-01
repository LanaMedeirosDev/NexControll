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

    // 1. Opcional - Forçar a aba "Contas a Pagar" a ficar ativa (caso a navegação não tenha feito)
    const contasPagarLink = document.querySelector('[data-page="contas-pagar"]');
    if (contasPagarLink && !contasPagarLink.classList.contains('active')) {
        document.querySelectorAll('.menu-link, .submenu-link').forEach(l => l.classList.remove('active'));
        contasPagarLink.classList.add('active');
        const parentSubmenu = contasPagarLink.closest('.has-submenu');
        if (parentSubmenu) {
            parentSubmenu.classList.add('open');
        }
    }

    // 2. Elementos da Tabela
    const tableBody = document.querySelector('#tabela-despesas tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    // Dados em memória para simulação se API cair
    let currentData = [];
    let isMockFallback = false;

    // 3. Busca de Despesas (API)
    async function fetchDespesas() {
        try {
            const response = await fetch('/despesas');
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            const data = await response.json();
            currentData = data;
            isMockFallback = false;
            renderTable(currentData);
        } catch (error) {
            console.warn('API /despesas inacessível. Usando mock dados localmente.', error);
            isMockFallback = true;
            loadingMessage.style.display = 'none';

            // Dados mockados
            currentData = [
                { id: 1, descricao: 'Fornecedor XYZ - Matéria Prima', valor: 2500.00, vencimento: '2026-03-18', status: 'PENDENTE' },
                { id: 2, descricao: 'Conta de Energia Elétrica', valor: 450.00, vencimento: '2026-03-10', status: 'ATRASADO' },
                { id: 3, descricao: 'Internet e Telefonia', valor: 299.90, vencimento: '2026-03-05', status: 'PAGO' },
                { id: 4, descricao: 'Manutenção de Equipamentos', valor: 1500.00, vencimento: '2026-03-25', status: 'PENDENTE' },
                { id: 5, descricao: 'Aluguel do Galpão', valor: 3000.00, vencimento: '2026-03-20', status: 'PENDENTE' }
            ];
            renderTable(currentData);
        }
    }

    // Funções auxiliares
    function formatCurrency(value) {
        return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
    }

    function formatDate(dateString) {
        const baseDate = dateString.split('T')[0];
        const parts = baseDate.split('-');
        if (parts.length === 3) {
            return `${parts[2]}/${parts[1]}/${parts[0]}`;
        }
        return dateString;
    }

    // 4. Renderizar Tabela e Cards
    function renderTable(data) {
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        let total = 0;
        let pago = 0;
        let pendente = 0;
        let atrasado = 0;

        if (!data || data.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5" style="text-align: center; padding: 3rem; color: var(--text-muted);">
                        Nenhuma conta a pagar encontrada.
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

            if (statusText === 'PENDENTE' || statusText === 'PENDING') {
                statusClass = 'status-pendente';
                statusText = 'Pendente';
                pendente += item.valor || 0;
            } else if (statusText === 'PAGO' || statusText === 'PAID') {
                statusClass = 'status-pago';
                statusText = 'Pago';
                pago += item.valor || 0;
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
                        <button class="btn-icon btn-pay" title="Marcar como Pago" data-action="pay" data-id="${item.id}">
                            <i data-lucide="check-circle"></i>
                        </button>
                        <button class="btn-icon btn-edit" title="Editar Despesa" data-action="edit" data-id="${item.id}">
                            <i data-lucide="edit-2"></i>
                        </button>
                        <button class="btn-icon btn-delete" title="Excluir Despesa" data-action="delete" data-id="${item.id}">
                            <i data-lucide="trash-2"></i>
                        </button>
                    </div>
                </td>
            `;
            tableBody.appendChild(tr);
        });

        updateCards(total, pago, pendente, atrasado);

        if (window.lucide) {
            window.lucide.createIcons();
        }
    }

    // Atualiza cards
    function updateCards(total, pago, pendente, atrasado) {
        document.getElementById('resumo-total').textContent = formatCurrency(total);
        document.getElementById('resumo-pago').textContent = formatCurrency(pago);
        document.getElementById('resumo-pendente').textContent = formatCurrency(pendente);
        document.getElementById('resumo-atrasado').textContent = formatCurrency(atrasado);

        const percEl = document.getElementById('chart-percent');
        if (total > 0) {
            const perc = ((pago / total) * 100).toFixed(0);
            percEl.textContent = perc + '%';
        } else {
            percEl.textContent = '0%';
        }
    }

    // ==========================================
    // DELEGAÇÃO DE EVENTOS PARA OS BOTÕES DA TABELA
    // ==========================================
    tableBody.addEventListener('click', async (e) => {
        const btn = e.target.closest('button');
        if (!btn) return;

        const action = btn.getAttribute('data-action');
        const id = parseInt(btn.getAttribute('data-id'), 10);

        if (action === 'delete') {
            if (confirm(`Deseja realmente excluir a despesa #${id}?`)) {
                await deleteDespesa(id);
            }
        } else if (action === 'pay') {
            if (confirm(`Marcar a despesa #${id} como paga?`)) {
                await payDespesa(id);
            }
        } else if (action === 'edit') {
            // Apenas simulando um prompt simplificado de edição
            const novaDesc = prompt(`Editar descrição para despesa #${id}:`);
            if (novaDesc) {
                await editDespesa(id, { descricao: novaDesc });
            }
        }
    });

    // Função Excluir
    async function deleteDespesa(id) {
        if (!isMockFallback) {
            try {
                const response = await fetch(`/despesas/${id}`, { method: 'DELETE' });
                if (!response.ok) throw new Error('Falha ao excluir.');
            } catch (err) {
                console.error(err);
                alert('Erro na API ao excluir. Removendo localmente para teste.');
            }
        }
        // Atualiza UI
        currentData = currentData.filter(d => d.id !== id);
        renderTable(currentData);
    }

    // Função Pagar
    async function payDespesa(id) {
        if (!isMockFallback) {
            try {
                const response = await fetch(`/despesas/${id}/pagar`, { method: 'PUT' });
                if (!response.ok) throw new Error('Falha ao marcar como pago.');
            } catch (err) {
                console.error(err);
                alert('Erro na API ao pagar. Atualizando localmente para teste.');
            }
        }
        // Atualiza UI
        const idx = currentData.findIndex(d => d.id === id);
        if (idx > -1) {
            currentData[idx].status = 'PAGO';
            renderTable(currentData);
        }
    }

    // Função Editar
    async function editDespesa(id, newData) {
        if (!isMockFallback) {
            try {
                // Montando payload simplificado
                const response = await fetch(`/despesas/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(newData)
                });
                if (!response.ok) throw new Error('Falha ao editar.');
            } catch (err) {
                console.error(err);
                alert('Erro na API ao editar. Atualizando localmente para teste.');
            }
        }
        // Atualiza UI
        const idx = currentData.findIndex(d => d.id === id);
        if (idx > -1) {
            currentData[idx] = { ...currentData[idx], ...newData };
            renderTable(currentData);
        }
    }

    // Inicializar
    fetchDespesas();
});

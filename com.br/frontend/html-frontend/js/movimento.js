// js/movimento.js

const API_BASE_URL = 'http://localhost:8080';

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

    // Carregar todos os dados inicialmente
    // Para respeitar os filtros ao iniciar, podemos passar a queryString ou carregar tudo.
    // Como a instrução diz "Ao carregar a página: Fazer GET /movimentofinanceiro Renderizar todos os dados",
    // deixaremos a requisição padrão sem filtros, mas os inputs estarão preenchidos.
    carregarMovimentos();

    const formFiltro = document.getElementById('form-filtro');
    if (formFiltro) {
        formFiltro.addEventListener('submit', (e) => {
            e.preventDefault();
            aplicarFiltros();
        });
    }
});

/**
 * Faz a requisição padrão ou parametrizada
 * @param {string} queryString URL complement, ex: ?dataInicio=...
 */
async function carregarMovimentos(queryString = '') {
    const tableBody = document.querySelector('#tabela-movimento tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    try {
        loadingMessage.style.display = 'flex';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        const url = `${API_BASE_URL}/movimentofinanceiro${queryString}`;
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const movimentos = await response.json();
        
        loadingMessage.style.display = 'none';
        renderizarTabela(movimentos);
        
    } catch (error) {
        console.error('Erro ao carregar os movimentos financeiros:', error);
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'flex';
        errorMessage.innerHTML = `<i data-lucide="alert-circle"></i> Ocorreu um erro de conexão. Verifique se a API está rodando.`;
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }
}

/**
 * Chamada quando o usuário clica em Filtrar
 */
function aplicarFiltros() {
    const dataInicial = document.getElementById('dataInicio').value;
    const dataFinal = document.getElementById('dataFim').value;
    const busca = document.getElementById('input-busca').value.trim();

    // Validação extra caso o HTML5 bypass passe em claro
    if (!dataInicial || !dataFinal) {
        alert("O filtro de data (inicial e final) é obrigatório.");
        return;
    }

    const queryParams = new URLSearchParams();
    queryParams.append('dataInicio', dataInicial);
    queryParams.append('dataFim', dataFinal);
    if (busca) {
        queryParams.append('busca', busca);
    }

    // Carregar a tabela com a QueryString gerada
    carregarMovimentos(`?${queryParams.toString()}`);
}

/**
 * Renderiza os dados no corpo da tabela
 */
function renderizarTabela(movimentos) {
    const tableBody = document.querySelector('#tabela-movimento tbody');
    tableBody.innerHTML = '';

    if (!movimentos || movimentos.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="6" class="text-center" style="padding: 2rem; color: var(--text-muted);">Nenhum movimento encontrado para os filtros selecionados.</td>`;
        tableBody.appendChild(tr);
        return;
    }

    movimentos.forEach(mov => {
        const tr = document.createElement('tr');

        // Tratamento dos atributos com fallbacks para evitar erros se nulos
        const tipo = (mov.tipo || '').toUpperCase(); // 'RECEITA' ou 'DESPESA'
        const isReceita = tipo === 'RECEITA';
        
        const badgeClass = isReceita ? 'badge receita' : 'badge despesa';
        const badgeLabel = isReceita ? 'Receita' : 'Despesa';
        const valorClass = isReceita ? 'valor-receita' : 'valor-despesa';
        
        const descricao = mov.titulo || mov.descricao || '--';
        const envolvido = mov.cliente || mov.fornecedor || '--'; 
        
        // Formaração do valor
        const valorNumerico = parseFloat(mov.valor || 0);
        const valorFormatado = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        const prefixoValor = isReceita ? '+' : '-';

        // Formatação de data simples (assumindo formato YYYY-MM-DD)
        let dataStr = mov.dataPagamento || '--';
        if (dataStr !== '--') {
            const dateObj = new Date(dataStr + "T00:00:00");
             if(!isNaN(dateObj)){
                 const dia = String(dateObj.getDate()).padStart(2, '0');
                 const mes = String(dateObj.getMonth() + 1).padStart(2, '0');
                 const ano = dateObj.getFullYear();
                 dataStr = `${dia}/${mes}/${ano}`;
             }
        }

        const contaFin = mov.contaFinanceira || '--';

        tr.innerHTML = `
            <td><span class="${badgeClass}">${badgeLabel}</span></td>
            <td><strong>${descricao}</strong></td>
            <td>${envolvido}</td>
            <td class="${valorClass}">${prefixoValor} ${valorFormatado}</td>
            <td>${dataStr}</td>
            <td>${contaFin}</td>
        `;

        tableBody.appendChild(tr);
    });

    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
}

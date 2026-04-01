// js/forma-pagamento.js

// Ajuste da porta conforme o backend
const API_BASE_URL = 'http://localhost:8080';

// Armazena contas para facilitar lookup do nome da conta na tabela, se a API de pagamentos não retornar
let contasDisponiveis = [];

document.addEventListener('DOMContentLoaded', () => {
    inicializarPagina();

    const formPagamento = document.getElementById('form-pagamento');
    if (formPagamento) {
        formPagamento.addEventListener('submit', salvarPagamento);
    }
});

/**
 * 1. Ao carregar a página: Fazer GET /contas e preencher select. Depois GET /pagamentos.
 */
async function inicializarPagina() {
    await carregarContas();
    await carregarPagamentos();
}

/**
 * Carrega as contas financeiras para preencher o select
 */
async function carregarContas() {
    const selectConta = document.getElementById('select-conta');

    try {
        const response = await fetch(`${API_BASE_URL}/contas`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP ao carregar contas: ${response.status}`);
        }

        const contas = await response.json();
        contasDisponiveis = contas;
        
        // Limpar select
        selectConta.innerHTML = '<option value="" disabled selected>Selecione a conta</option>';
        
        contas.forEach(conta => {
            const option = document.createElement('option');
            option.value = conta.id; // assumindo que a API retorna 'id', ou adapte se for outro campo
            option.textContent = conta.nome || 'Sem nome';
            selectConta.appendChild(option);
        });

    } catch (error) {
        console.error('Erro ao carregar lista de contas:', error);
        selectConta.innerHTML = '<option value="" disabled selected>Erro ao carregar contas</option>';
    }
}

/**
 * Carrega a tabela de pagamentos
 */
async function carregarPagamentos() {
    const tableBody = document.querySelector('#tabela-pagamentos tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    try {
        loadingMessage.style.display = 'flex';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        const response = await fetch(`${API_BASE_URL}/pagamentos`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const pagamentos = await response.json();
        
        loadingMessage.style.display = 'none';
        renderizarTabela(pagamentos);
        
    } catch (error) {
        console.error('Erro ao carregar pagamentos:', error);
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'flex';
        errorMessage.innerHTML = `<i data-lucide="alert-circle"></i> Ocorreu um erro de conexão. Tente novamente mais tarde.`;
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }
}

/**
 * Renderiza os pagamentos na tabela dinamicamente.
 */
function renderizarTabela(pagamentos) {
    const tableBody = document.querySelector('#tabela-pagamentos tbody');
    tableBody.innerHTML = '';

    if (!pagamentos || pagamentos.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="2" class="text-center" style="padding: 2rem; color: var(--text-muted);">Nenhuma forma de pagamento cadastrada.</td>`;
        tableBody.appendChild(tr);
        return;
    }

    pagamentos.forEach(pagamento => {
        const tr = document.createElement('tr');
        
        // Simples tratamento caso falte algum atributo na API
        const nomePagamento = pagamento.nome || 'Forma sem nome';
        
        // Tentar obter o nome da conta seja da entidade aninhada pagamento.conta.nome, ou via contasDisponiveis
        let nomeConta = '--';
        if (pagamento.conta && pagamento.conta.nome) {
            nomeConta = pagamento.conta.nome;
        } else if (pagamento.contaId) {
            const contaRef = contasDisponiveis.find(c => String(c.id) === String(pagamento.contaId));
            if (contaRef) nomeConta = contaRef.nome;
        }
        
        tr.innerHTML = `
            <td><strong>${nomePagamento}</strong></td>
            <td>${nomeConta}</td>
        `;
        tableBody.appendChild(tr);
    });

    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
}

/**
 * 2. Ao clicar em "Salvar": Capturar, validar, POST /pagamentos
 */
async function salvarPagamento(event) {
    event.preventDefault();

    const inputNome = document.getElementById('input-nome-pagamento');
    const selectConta = document.getElementById('select-conta');
    
    const nome = inputNome.value.trim();
    const contaIdStr = selectConta.value;
    const btnSalvar = document.querySelector('.btn-salvar-pagamento');

    if (!nome) {
        alert('Por favor, digite o nome da forma de pagamento.');
        inputNome.focus();
        return;
    }

    if (!contaIdStr) {
        alert('Por favor, selecione uma conta financeira associada.');
        selectConta.focus();
        return;
    }

    try {
        const originalText = btnSalvar.innerText;
        btnSalvar.innerText = 'Salvando...';
        btnSalvar.disabled = true;

        const payload = {
            nome: nome,
            // Certifique-se de que o backend espera um inteiro para contaId se ele for numerico, parsando-o:
            contaId: isNaN(parseInt(contaIdStr)) ? contaIdStr : parseInt(contaIdStr)
        };

        const response = await fetch(`${API_BASE_URL}/pagamentos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error(`Erro HTTP ao salvar: ${response.status}`);
        }

        // 3. Após salvar: Limpar inputs, resetar select e atualizar tabela
        inputNome.value = '';
        selectConta.value = ''; // volta para placeholder
        inputNome.focus();
        
        // Feedback visual amigável (temporário verde)
        btnSalvar.innerText = 'Salvo!';
        btnSalvar.style.backgroundColor = '#10b981'; 
        
        setTimeout(() => {
            btnSalvar.innerText = originalText;
            btnSalvar.style.backgroundColor = '';
            btnSalvar.disabled = false;
        }, 2000);

        carregarPagamentos();

    } catch (error) {
        console.error('Erro ao salvar forma de pagamento:', error);
        alert('Erro ao processar a requisição. Verifique sua conexão ou tente mais tarde.');
        btnSalvar.innerText = 'Salvar';
        btnSalvar.disabled = false;
    }
}

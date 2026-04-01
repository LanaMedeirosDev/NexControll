// js/conta-financeira.js

// Ajuste da porta conforme o backend
const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    carregarContas();

    const formConta = document.getElementById('form-conta');
    if (formConta) {
        formConta.addEventListener('submit', salvarConta);
    }
});

/**
 * 1. Ao carregar a página: Fazer GET /contas e renderizar a tabela
 */
async function carregarContas() {
    const tableBody = document.querySelector('#tabela-contas tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    try {
        loadingMessage.style.display = 'flex';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        const response = await fetch(`${API_BASE_URL}/contas`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const contas = await response.json();
        
        loadingMessage.style.display = 'none';
        renderizarTabela(contas);
        
    } catch (error) {
        console.error('Erro ao carregar contas:', error);
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'flex';
        errorMessage.innerHTML = `<i data-lucide="alert-circle"></i> Ocorreu um erro de conexão. Tente novamente mais tarde.`;
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }
}

/**
 * Renderiza as contas na tabela dinamicamente.
 */
function renderizarTabela(contas) {
    const tableBody = document.querySelector('#tabela-contas tbody');
    tableBody.innerHTML = '';

    if (!contas || contas.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="3" class="text-center" style="padding: 2rem; color: var(--text-muted);">Nenhuma conta cadastrada nesta conta.</td>`;
        tableBody.appendChild(tr);
        return;
    }

    contas.forEach(conta => {
        const tr = document.createElement('tr');
        
        // Simples tratamento caso falte algum atributo na API (banco, boleto)
        const nomeConta = conta.nome || 'Conta sem nome';
        const banco = conta.banco || '--';
        const emiteBoleto = conta.boleto ? 'checked' : '';
        
        tr.innerHTML = `
            <td><strong>${nomeConta}</strong></td>
            <td>${banco}</td>
            <td class="text-center" style="display:flex; justify-content:center; border:none; border-bottom: 1px solid rgba(139, 138, 155, 0.15);">
                <div class="visual-checkbox ${emiteBoleto}">
                    <i data-lucide="check" style="width: 14px; height: 14px;"></i>
                </div>
            </td>
        `;
        tableBody.appendChild(tr);
    });

    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
}

/**
 * 2. Ao clicar em "Salvar": Capturar, validar, POST /contas
 */
async function salvarConta(event) {
    event.preventDefault();

    const inputNome = document.getElementById('input-nome-conta');
    const nome = inputNome.value.trim();
    const btnSalvar = document.querySelector('.btn-salvar-conta');

    if (!nome) {
        alert('Por favor, digite o nome da conta.');
        inputNome.focus();
        return;
    }

    try {
        const originalText = btnSalvar.innerText;
        btnSalvar.innerText = 'Salvando...';
        btnSalvar.disabled = true;

        const response = await fetch(`${API_BASE_URL}/contas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome: nome })
        });

        if (!response.ok) {
            throw new Error(`Erro HTTP ao salvar: ${response.status}`);
        }

        // 3. Após salvar: Limpar input e atualizar tabela sem reload
        inputNome.value = '';
        inputNome.focus();
        
        // Feedback visual amigável (temporário verde)
        btnSalvar.innerText = 'Salvo!';
        btnSalvar.style.backgroundColor = '#10b981'; 
        
        setTimeout(() => {
            btnSalvar.innerText = originalText;
            btnSalvar.style.backgroundColor = '';
            btnSalvar.disabled = false;
        }, 2000);

        carregarContas();

    } catch (error) {
        console.error('Erro ao salvar conta:', error);
        alert('Erro ao processar a requisição. Verifique sua conexão ou tente mais tarde.');
        btnSalvar.innerText = 'Salvar';
        btnSalvar.disabled = false;
    }
}

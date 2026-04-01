// js/produtos.js

const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    carregarProdutos();

    // Setup Modal interactions
    const btnNovoProduto = document.getElementById('btn-novo-produto');
    const btnCloseModal = document.getElementById('btn-close-modal');
    const btnCancelar = document.getElementById('btn-cancelar');
    const btnSalvar = document.getElementById('btn-salvar');

    if (btnNovoProduto) {
        btnNovoProduto.addEventListener('click', () => {
            abrirModal();
        });
    }

    [btnCloseModal, btnCancelar].forEach(btn => {
        if (btn) btn.addEventListener('click', fecharModal);
    });

    if (btnSalvar) {
        btnSalvar.addEventListener('click', salvarProduto);
    }
});

function abrirModal() {
    const modal = document.getElementById('modal-produto');
    const form = document.getElementById('form-produto');
    form.reset();
    document.getElementById('produto-id').value = '';
    
    // Set default value for select if necessary
    document.getElementById('produto-tipo').value = 'PRODUTO';
    
    document.getElementById('modal-title').innerText = 'Novo Produto/Serviço';
    modal.style.display = 'flex';
}

function fecharModal() {
    document.getElementById('modal-produto').style.display = 'none';
}

async function carregarProdutos() {
    const tableBody = document.querySelector('#tabela-produtos tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');

    try {
        loadingMessage.style.display = 'flex';
        errorMessage.style.display = 'none';
        tableBody.innerHTML = '';

        const response = await fetch(`${API_BASE_URL}/produtos`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const produtos = await response.json();
        
        loadingMessage.style.display = 'none';
        renderizarTabela(produtos);
        
    } catch (error) {
        console.error('Erro ao carregar os produtos:', error);
        loadingMessage.style.display = 'none';
        errorMessage.style.display = 'flex';
        errorMessage.innerHTML = `<i data-lucide="alert-circle"></i> Não foi possível carregar os dados.`;
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }
}

function renderizarTabela(produtos) {
    const tableBody = document.querySelector('#tabela-produtos tbody');
    tableBody.innerHTML = '';

    if (!produtos || produtos.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="7" class="text-center" style="padding: 2rem; color: var(--text-muted);">Nenhum cadastro encontrado.</td>`;
        tableBody.appendChild(tr);
        return;
    }

    produtos.forEach(produto => {
        const tr = document.createElement('tr');
        
        const nome = produto.nomeDoProduto || '--';
        const ncm = produto.ncm || '--';
        
        const valorVendaNumerico = parseFloat(produto.valorDeVenda || 0);
        const valorVendaFormatado = valorVendaNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        
        const unidade = produto.unidadeDeMedida || '--';
        
        // Handle visual badge for type
        const tipo = (produto.tipoDeCadastroProduto || 'PRODUTO').toUpperCase();
        const tipoHtml = tipo === 'SERVICO' 
            ? `<span style="background: rgba(139, 92, 246, 0.15); color: #8b5cf6; padding: 2px 8px; border-radius: 6px; font-size: 0.8rem; font-weight: 600;">SERVIÇO</span>`
            : `<span style="background: rgba(52, 211, 153, 0.15); color: #34d399; padding: 2px 8px; border-radius: 6px; font-size: 0.8rem; font-weight: 600;">PRODUTO</span>`;
            
        const barCode = produto.codigoDeBarras || '--';

        tr.innerHTML = `
            <td><strong>${nome}</strong></td>
            <td>${ncm}</td>
            <td style="color: #34d399; font-weight: 600;">${valorVendaFormatado}</td>
            <td>${unidade}</td>
            <td>${tipoHtml}</td>
            <td>${barCode}</td>
            <td class="actions-col">
                <div class="action-buttons">
                    <button class="btn-icon btn-edit" title="Editar"><i data-lucide="edit-2"></i></button>
                    <button class="btn-icon btn-delete" title="Excluir"><i data-lucide="trash-2"></i></button>
                </div>
            </td>
        `;

        tableBody.appendChild(tr);
    });

    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
}

async function salvarProduto(event) {
    if (event) event.preventDefault();

    const form = document.getElementById('form-produto');
    // Validar regras do HTML (required elements)
    if (!form.reportValidity()) return;

    const btnSalvar = document.getElementById('btn-salvar');
    
    // Capturar campos
    const nomeDoProduto = document.getElementById('produto-nome').value.trim();
    const tipoDeCadastroProduto = document.getElementById('produto-tipo').value;
    const ncm = document.getElementById('produto-ncm').value.trim();
    const unidadeDeMedida = document.getElementById('produto-unidade').value.trim();
    
    // Valores monetários, default para NULL uo 0 se inválido. Mas Venda é obrigatório.
    const valorDeVendaStr = document.getElementById('produto-venda').value;
    const valorDeVenda = valorDeVendaStr ? parseFloat(valorDeVendaStr) : 0.00;
    
    const valorDeCustoStr = document.getElementById('produto-custo').value;
    const valorDeCusto = valorDeCustoStr ? parseFloat(valorDeCustoStr) : null;

    // Campos opcionais de códigos
    const codigoSku = document.getElementById('produto-sku').value.trim();
    const codigoDeBarras = document.getElementById('produto-barras').value.trim();
    const cfopPreferencial = document.getElementById('produto-cfop').value.trim();

    try {
        btnSalvar.innerText = 'Salvando...';
        btnSalvar.disabled = true;

        // Montar estrutura JSON exata para API
        const payload = {
            nomeDoProduto: nomeDoProduto,
            ncm: ncm,
            valorDeVenda: valorDeVenda,
            valorDeCusto: valorDeCusto,
            unidadeDeMedida: unidadeDeMedida,
            codigoSku: codigoSku || null,
            codigoDeBarras: codigoDeBarras || null,
            cfopPreferencial: cfopPreferencial || null,
            tipoDeCadastroProduto: tipoDeCadastroProduto,
            ativoProduto: 1 // Conforme regra passada
        };

        const response = await fetch(`${API_BASE_URL}/produtos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error(`Erro HTTP ao salvar: ${response.status}`);
        }

        fecharModal();
        carregarProdutos(); // Reload the table instantly

    } catch (error) {
        console.error('Erro ao salvar produto:', error);
        alert('Falha ao salvar o produto/serviço. Por favor, tente novamente.');
    } finally {
        btnSalvar.innerText = 'Salvar Cadastro';
        btnSalvar.disabled = false;
    }
}

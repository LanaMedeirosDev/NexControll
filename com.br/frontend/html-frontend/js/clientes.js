document.addEventListener('DOMContentLoaded', () => {

    // 1. Elementos da Interface
    const tableBody = document.querySelector('#tabela-clientes tbody');
    const loadingMessage = document.getElementById('loading-message');
    const errorMessage = document.getElementById('error-message');
    
    // Elementos de Paginação
    const btnPrev = document.getElementById('btn-prev');
    const btnNext = document.getElementById('btn-next');
    const pageInfo = document.getElementById('page-info');

    // Elementos do Modal
    const modal = document.getElementById('modal-cliente');
    const btnNovoCliente = document.getElementById('btn-novo-cliente');
    const btnCloseModal = document.getElementById('btn-close-modal');
    const btnCancelar = document.getElementById('btn-cancelar');
    const btnSalvar = document.getElementById('btn-salvar');
    const formCliente = document.getElementById('form-cliente');
    const modalTitle = document.getElementById('modal-title');

    // Campos do formulário
    const inputId = document.getElementById('cliente-id');
    const inputNome = document.getElementById('cliente-nome');
    const inputDocumento = document.getElementById('cliente-documento');
    const inputTipoPessoa = document.getElementById('cliente-tipo-pessoa');
    const inputIeRg = document.getElementById('cliente-ie-rg');
    const inputTipoCadastro = document.getElementById('cliente-tipo-cadastro');
    
    // Contato
    const inputTelefone = document.getElementById('cliente-telefone');
    const inputCelular = document.getElementById('cliente-celular');

    // Endereco
    const inputCep = document.getElementById('cliente-cep');
    const inputLogradouro = document.getElementById('cliente-logradouro');
    const inputNumero = document.getElementById('cliente-numero');
    const inputComplemento = document.getElementById('cliente-complemento');
    const inputBairro = document.getElementById('cliente-bairro');
    const inputCidade = document.getElementById('cliente-cidade');
    const inputUf = document.getElementById('cliente-uf');

    const inputDataCadastro = document.getElementById('cliente-data_cadastro');

    let currentPage = 1;
    let limit = 10;
    let currentData = [];
    
    // URL base da API Spring Boot
    const API_BASE_URL = 'http://127.0.0.1:8080';

    // Lógica IE/RG obrigatório
    inputTipoPessoa.addEventListener('change', () => {
        if (inputTipoPessoa.value === 'Pessoa Jurídica') {
            inputIeRg.required = true;
            inputIeRg.placeholder = 'Inscrição Estadual (Obrigatório)';
        } else {
            inputIeRg.required = false;
            inputIeRg.placeholder = 'RG (Opcional)';
        }
    });

    // 2. Controlar Abrir/Fechar Modal
    function openModal(isEdit = false, cliente = null) {
        modal.style.display = 'flex';
        
        if (isEdit && cliente) {
            modalTitle.textContent = 'Editar Cliente';
            inputId.value = cliente.id || '';
            inputNome.value = cliente.nome || '';
            inputDocumento.value = cliente.cpfCnpj || '';
            inputTipoPessoa.value = cliente.tipoPessoa || 'Pessoa Física';
            inputIeRg.value = cliente.ieRg || '';
            inputTipoCadastro.value = cliente.tipoDeCadastro || cliente.tipoCadastro || 'Cliente';
            
            inputTelefone.value = cliente.telefone || '';
            inputCelular.value = cliente.celular || '';

            // Endereço pode estar aninhado
            const endereco = cliente.endereco || {};
            inputCep.value = endereco.cep || '';
            inputLogradouro.value = endereco.logradouro || '';
            inputNumero.value = endereco.numero || '';
            inputComplemento.value = endereco.complemento || '';
            inputBairro.value = endereco.bairro || '';
            inputCidade.value = endereco.cidade || '';
            inputUf.value = endereco.uf || '';

            inputDataCadastro.value = cliente.dataCadastro || '';
            
            // Trigger change manual para setar o IE/RG rules
            inputTipoPessoa.dispatchEvent(new Event('change'));
        } else {
            modalTitle.textContent = 'Novo Cliente';
            formCliente.reset();
            inputId.value = '';
            inputDataCadastro.value = '';
            inputTipoPessoa.dispatchEvent(new Event('change'));
        }
    }

    function closeModal() {
        modal.style.display = 'none';
        formCliente.reset();
        inputId.value = '';
    }

    btnNovoCliente.addEventListener('click', () => openModal(false));
    btnCloseModal.addEventListener('click', closeModal);
    btnCancelar.addEventListener('click', closeModal);

    window.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });

    // 3. Buscar Clientes (API)
    async function fetchClientes(page = 1) {
        loadingMessage.style.display = 'flex';
        tableBody.innerHTML = '';
        errorMessage.style.display = 'none';

        try {
            const response = await fetch(`${API_BASE_URL}/clientes?page=${page - 1}&size=${limit}`);
            if (!response.ok) throw new Error(`Erro HTTP: ${response.status}`);
            
            const data = await response.json();
            // Spring Data retorna a paginação em 'content'
            const clientesList = data.content || [];
            
            currentData = clientesList;
            currentPage = page;
            renderTable(currentData);
            updatePaginationUI();
        } catch (error) {
            console.error('Erro ao buscar clientes da API:', error);
            loadingMessage.style.display = 'none';
            errorMessage.style.display = 'flex';
            currentData = [];
            renderTable([]);
        }
    }

    // 4. Formatações
    function formatDate(dateString) {
        if (!dateString) return '-';
        const baseDate = dateString.split('T')[0];
        const parts = baseDate.split('-');
        if (parts.length === 3) return `${parts[2]}/${parts[1]}/${parts[0]}`;
        return dateString;
    }

    // Remover formatação de campos numéricos
    function somenteDigitos(texto) {
        return (texto || '').replace(/\D/g, '');
    }

    // 5. Renderizar Tabela
    function renderTable(data) {
        loadingMessage.style.display = 'none';
        tableBody.innerHTML = '';

        if (!data || data.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align: center; padding: 3rem; color: var(--text-muted);">
                        Nenhum cliente ativo encontrado.
                    </td>
                </tr>`;
            return;
        }

        data.forEach(item => {
            const tr = document.createElement('tr');
            
            // Botão clicavel verde (Ativo)
            const btnStatus = `<button class="btn-status-active" data-action="inativar-status" data-id="${item.id}" title="Clique para inativar">
                                  <span class="status-dot dot-active"></span> Ativo
                               </button>`;

            const iniciais = (item.nome || 'Cliente').substring(0, 2).toUpperCase();
            const documento = item.cpfCnpj || '-';
            const tipoCad = item.tipoDeCadastro || '-';

            tr.innerHTML = `
                <td>
                    <div class="client-info">
                        <div class="client-avatar">${iniciais}</div>
                        <div>
                            <div style="font-weight: 500; color: var(--text-main); margin-bottom: 2px;">${item.nome}</div>
                            <div style="font-size: 0.8rem; color: var(--text-muted);">Cód: #${item.id}</div>
                        </div>
                    </div>
                </td>
                <td>${documento}</td>
                <td>${tipoCad}</td>
                <td>${formatDate(item.dataCadastro || item.createdAt)}</td>
                <td>${btnStatus}</td>
                <td class="actions-col">
                    <div class="action-buttons">
                        <button class="btn-icon btn-edit" title="Editar" data-action="edit" data-id="${item.id}">
                            <i data-lucide="edit-2"></i>
                        </button>
                        <button class="btn-icon btn-delete" title="Inativar Cliente" data-action="delete" data-id="${item.id}">
                            <i data-lucide="trash-2"></i>
                        </button>
                    </div>
                </td>
            `;
            tableBody.appendChild(tr);
        });

        if (window.lucide) {
            window.lucide.createIcons();
        }
    }

    // 6. Atualizar UI da Paginação
    function updatePaginationUI() {
        pageInfo.textContent = `Mostrando página ${currentPage}`;
        btnPrev.disabled = currentPage === 1;
        btnNext.disabled = currentData.length < limit;
    }

    btnPrev.addEventListener('click', () => {
        if (currentPage > 1) fetchClientes(currentPage - 1);
    });

    btnNext.addEventListener('click', () => {
        fetchClientes(currentPage + 1);
    });

    // 7. Delegação de Eventos (Botões na Tabela)
    tableBody.addEventListener('click', (e) => {
        const btn = e.target.closest('button');
        if (!btn) return;

        const action = btn.getAttribute('data-action');
        const id = parseInt(btn.getAttribute('data-id'), 10);
        const cliente = currentData.find(c => c.id === id);

        if (action === 'edit' && cliente) {
            openModal(true, cliente);
        } else if (action === 'delete' || action === 'inativar-status') {
            inativarCliente(id);
        }
    });

    // 8. CRUD: Salvar Cliente (POST ou PUT)
    btnSalvar.addEventListener('click', async () => {
        if (!formCliente.checkValidity()) {
            formCliente.reportValidity();
            return;
        }

        const id = inputId.value;
        
        // Estrutura correta conforme esperado pelo backend
        let payload;
        
        if (id) {
            // Para PUT (atualizar)
            payload = {
                id: parseInt(id),
                nome: inputNome.value,
                cpfCnpj: somenteDigitos(inputDocumento.value),
                tipoPessoa: inputTipoPessoa.value,
                ieRg: somenteDigitos(inputIeRg.value),
                tipoDeCadastro: inputTipoCadastro.value,
                telefone: somenteDigitos(inputTelefone.value),
                celular: somenteDigitos(inputCelular.value),
                endereco: {
                    cep: somenteDigitos(inputCep.value),
                    logradouro: inputLogradouro.value,
                    numero: inputNumero.value,
                    complemento: inputComplemento.value,
                    bairro: inputBairro.value,
                    cidade: inputCidade.value,
                    uf: inputUf.value.toUpperCase()
                }
            };
        } else {
            // Para POST (criar)
            payload = {
                nome: inputNome.value,
                cpfCnpj: somenteDigitos(inputDocumento.value),
                tipoPessoa: inputTipoPessoa.value,
                ieRg: somenteDigitos(inputIeRg.value),
                tipoDeCadastro: inputTipoCadastro.value,
                telefone: somenteDigitos(inputTelefone.value),
                celular: somenteDigitos(inputCelular.value),
                endereco: {
                    cep: somenteDigitos(inputCep.value),
                    logradouro: inputLogradouro.value,
                    numero: inputNumero.value,
                    complemento: inputComplemento.value,
                    bairro: inputBairro.value,
                    cidade: inputCidade.value,
                    uf: inputUf.value.toUpperCase()
                }
            };
        }

        const method = id ? 'PUT' : 'POST';
        const url = `${API_BASE_URL}/clientes`;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            
            if (!response.ok) {
                const errorData = await response.text();
                console.error('Resposta erro:', errorData);
                throw new Error(`Erro ${response.status}: ${errorData}`);
            }

            if (id) {
                alert('Sucesso, as informações foram atualizadas');
            } else {
                alert('Cliente cadastrado com sucesso');
            }
            closeModal();
            fetchClientes(currentPage); 
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao se comunicar com a API. Verifique o console para detalhes.');
        }
    });

    // 9. CRUD: Inativar (Soft Delete)
    async function inativarCliente(id) {
        const confirmacao = confirm('Deseja inativar o item selecionado? (Soft Delete)');
        if (!confirmacao) return;

        try {
            const response = await fetch(`${API_BASE_URL}/clientes/${id}`, { method: 'DELETE' });
            if (!response.ok) throw new Error('Falha ao inativar cliente na API');
            
            alert('Cliente inativado com sucesso');
            fetchClientes(currentPage);
        } catch (err) {
            console.error(err);
            alert('Erro ao inativar cliente. Verifique a conexão com a API.');
        }
    }

    // 10. Inicializar: Carregar dados dos clientes na abertura da página
    fetchClientes(1);

    // Renderizar ícones do Lucide quando disponível
    if (window.lucide) {
        window.lucide.createIcons();
    }
});

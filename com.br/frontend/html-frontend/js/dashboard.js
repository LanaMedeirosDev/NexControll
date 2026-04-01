document.addEventListener("DOMContentLoaded", () => {
    // 1. Inicializa os ícones SVG do Lucide
    lucide.createIcons();

    // 2. Elementos DOM do Sidebar e Conteúdo
    const sidebar = document.getElementById('sidebar');
    const toggles = sidebar.querySelectorAll('.submenu-toggle'); // Links pai
    const allLinks = sidebar.querySelectorAll('.menu-link, .submenu-link'); // Interativos

    // ==========================================
    // Mecanismo Accordion (Abertura e Fechamento)
    // ==========================================
    toggles.forEach(toggle => {
        toggle.addEventListener('click', function (e) {
            e.preventDefault(); 
            
            const parentLi = this.parentElement; 
            const parentUl = parentLi.parentElement; 
            
            // Fechar OUTROS Submenus abertos do mesmo nível
            const siblings = Array.from(parentUl.children).filter(child => child.classList.contains('has-submenu'));
            siblings.forEach(sibling => {
                if (sibling !== parentLi && sibling.classList.contains('open')) {
                    sibling.classList.remove('open');
                }
            });

            // Abrir ou Fechar o clicado
            parentLi.classList.toggle('open');
        });
    });

    // ==========================================
    // Lógica de Ativação de abas (Highlight)
    // ==========================================
    allLinks.forEach(link => {
        if (link.classList.contains('submenu-toggle')) return;

        link.addEventListener('click', function (e) {
            // Em uma app multipage (Múltiplos arquivos HTML), não faríamos preventDefault.
            // Aqui estamos apenas controlando o visual.
            // e.preventDefault(); 

            // Remove class de todos
            allLinks.forEach(l => l.classList.remove('active'));

            // Adiciona no clicado
            this.classList.add('active');

            const pageId = this.getAttribute('data-page');
            console.log(`Navegando para: ${pageId}`);
            
            // Se for SPA pura, você pode fazer uma troca de conteúdo visível:
            // document.querySelectorAll('.dashboard-grid').forEach(v => v.style.display = 'none');
            // if(pageId === 'dashboard') target.style.display = 'grid';
        });
    });
});

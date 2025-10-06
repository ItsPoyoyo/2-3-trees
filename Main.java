public class Main {
    public static void main(String[] args) {
        Arvore23 arvore = new Arvore23();
        
        System.out.println("=== TESTE ÁRVORE 2-3 ===");
        
        // Teste 1: Inserção básica
        System.out.println("\n--- Teste 1: Inserções básicas ---");
        int[] valores1 = {10, 20, 5};
        for (int valor : valores1) {
            System.out.println("Inserindo: " + valor);
            arvore.inserir(valor);
            imprimirArvore(arvore);
        }
        
        // Teste 2: Inserções que causam splits
        System.out.println("\n--- Teste 2: Inserções com splits ---");
        int[] valores2 = {15, 25, 3, 8, 12};
        for (int valor : valores2) {
            System.out.println("Inserindo: " + valor);
            arvore.inserir(valor);
            imprimirArvore(arvore);
        }
        
        // Teste 3: Mais inserções complexas
        System.out.println("\n--- Teste 3: Inserções complexas ---");
        int[] valores3 = {30, 1, 18, 22};
        for (int valor : valores3) {
            System.out.println("Inserindo: " + valor);
            arvore.inserir(valor);
            imprimirArvore(arvore);
        }
        
        // Teste 4: Caso específico para testar reorganização
        System.out.println("\n--- Teste 4: Caso específico ---");
        Arvore23 arvore2 = new Arvore23();
        int[] valores4 = {5, 10, 15, 20, 25, 30, 35, 40};
        for (int valor : valores4) {
            System.out.println("Inserindo: " + valor);
            arvore2.inserir(valor);
            imprimirArvore(arvore2);
        }
    }
    
    // Método auxiliar para imprimir a árvore
    public static void imprimirArvore(Arvore23 arvore) {
        try {
            java.lang.reflect.Field raizField = Arvore23.class.getDeclaredField("raiz");
            raizField.setAccessible(true);
            No raiz = (No) raizField.get(arvore);
            
            System.out.println("Estrutura da árvore:");
            if (raiz == null) {
                System.out.println("Árvore vazia");
            } else {
                imprimirNo(raiz, 0);
            }
            System.out.println("-------------------");
        } catch (Exception e) {
            System.out.println("Erro ao imprimir árvore: " + e.getMessage());
        }
    }
    
    private static void imprimirNo(No no, int nivel) {
        if (no == null) return;
        
        String indent = "  ".repeat(nivel);
        
        // Imprime as chaves do nó
        System.out.print(indent + "Nó[");
        for (int i = 0; i < no.qtdChaves; i++) {
            System.out.print(no.chaves[i]);
            if (i < no.qtdChaves - 1) System.out.print(", ");
        }
        System.out.println("] (qtd: " + no.qtdChaves + ", folha: " + no.isFolha + ")");
        
        // Imprime os filhos recursivamente
        if (!no.isFolha) {
            for (int i = 0; i <= no.qtdChaves; i++) {
                imprimirNo(no.filhos[i], nivel + 1);
            }
        }
    }
}

// classe do no da árvore
class No {
    int[] chaves = new int[2];
    No[] filhos = new No[3];
    No pai;
    int qtdChaves = 0;
    boolean isFolha = true;

    No(No pai) {
        this.pai = pai;
    }
}

// classe principal da arvore 2-3
public class Arvore23 {
    private No raiz;

    public void inserir(int valor) {
        if (raiz == null) {
            raiz = new No(null);
            raiz.chaves[0] = valor;
            raiz.qtdChaves = 1;
            return;
        }
        inserirRec(raiz, valor);
    }

    private void inserirRec(No no, int valor) {
        if (no.isFolha) {
            if (no.qtdChaves < 2) {
                addChave(no, valor);
            } else {
                dividirFolha(no, valor);
            }
        } else {
            // decidir pra qual filho descer
            if (valor < no.chaves[0]) {
                inserirRec(no.filhos[0], valor);
            } else if (no.qtdChaves == 1 || valor < no.chaves[1]) {
                inserirRec(no.filhos[1], valor);
            } else {
                inserirRec(no.filhos[2], valor);
            }
        }
    }

    private void addChave(No no, int valor) {
        no.chaves[no.qtdChaves] = valor;
        no.qtdChaves++;

        // isso deixa ou garante que as arvores estejam em ordem
        if (no.qtdChaves == 2 && no.chaves[0] > no.chaves[1]) {
            int temp = no.chaves[0];
            no.chaves[0] = no.chaves[1];
            no.chaves[1] = temp;
        }
    }

    private void dividirFolha(No folha, int valor) {
        int a = folha.chaves[0];
        int b = folha.chaves[1];
        int menor, meio, maior;

        if (valor < a) {
            menor = valor; meio = a; maior = b;
        } else if (valor < b) {
            menor = a; meio = valor; maior = b;
        } else {
            menor = a; meio = b; maior = valor;
        }

        folha.chaves[0] = menor;
        folha.qtdChaves = 1;

        No novoIrmao = new No(folha.pai);
        novoIrmao.chaves[0] = maior;
        novoIrmao.qtdChaves = 1;

        subirChave(folha, meio, novoIrmao);
    }

    private void subirChave(No noEsq, int chaveSubindo, No noDir) {
        if (noEsq.pai == null) {
            // cria nova raiz
            No novaRaiz = new No(null);
            novaRaiz.chaves[0] = chaveSubindo;
            novaRaiz.qtdChaves = 1;
            novaRaiz.isFolha = false;

            novaRaiz.filhos[0] = noEsq;
            novaRaiz.filhos[1] = noDir;
            noEsq.pai = novaRaiz;
            noDir.pai = novaRaiz;

            raiz = novaRaiz;
        } else {
            No pai = noEsq.pai;
            if (pai.qtdChaves < 2) {
                addNoPai(pai, chaveSubindo, noEsq, noDir);
            } else {
                dividirPai(pai, chaveSubindo, noEsq, noDir);
            }
        }
    }

    private void addNoPai(No pai, int chave, No esq, No dir) {
        if (chave < pai.chaves[0]) {
            pai.chaves[1] = pai.chaves[0];
            pai.chaves[0] = chave;
            pai.filhos[2] = pai.filhos[1];
            pai.filhos[0] = esq;
            pai.filhos[1] = dir;
        } else {
            pai.chaves[1] = chave;
            pai.filhos[1] = esq;
            pai.filhos[2] = dir;
        }
        pai.qtdChaves++;
        pai.isFolha = false;
        esq.pai = pai;
        dir.pai = pai;
    }

    private void dividirPai(No pai, int chave, No filhoEsq, No filhoDir) {
        int a = pai.chaves[0];
        int b = pai.chaves[1];
        No f0 = pai.filhos[0];
        No f1 = pai.filhos[1];
        No f2 = pai.filhos[2];

        int menor, meio, maior;
        No c0, c1, c2, c3;

        if (chave < a) {
            menor = chave; meio = a; maior = b;
            c0 = filhoEsq; c1 = filhoDir; c2 = f1; c3 = f2;
        } else if (chave < b) {
            menor = a; meio = chave; maior = b;
            c0 = f0; c1 = filhoEsq; c2 = filhoDir; c3 = f2;
        } else {
            menor = a; meio = b; maior = chave;
            c0 = f0; c1 = f1; c2 = filhoEsq; c3 = filhoDir;
        }

        pai.chaves[0] = menor;
        pai.qtdChaves = 1;
        pai.filhos[0] = c0;
        pai.filhos[1] = c1;
        c0.pai = pai;
        c1.pai = pai;

        No novoIrmao = new No(pai.pai);
        novoIrmao.chaves[0] = maior;
        novoIrmao.qtdChaves = 1;
        novoIrmao.isFolha = false;
        novoIrmao.filhos[0] = c2;
        novoIrmao.filhos[1] = c3;
        c2.pai = novoIrmao;
        c3.pai = novoIrmao;

        subirChave(pai, meio, novoIrmao);
    }
}

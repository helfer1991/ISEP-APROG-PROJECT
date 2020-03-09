package campeonato;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

public class Campeonato {    
//    static final String FICHEIRO = "‪PracticalWork.csv";
    
    public static void main(String[] args) throws FileNotFoundException {
        String[][] equipas = new String[32][2]; //grupo e nome
        int[][] estatisticas = new int[32][9]; //jogos, vitorias, empates, derrotas, golos marcados, golos sofridos, pontos, diferença de golos, posição
        int[] rows = new int[1];
        int[] linhas = new int[1];
        String[][] novasEquipas = new String[16][2]; //o tamanho do array n poderá ser mudado posteriormente, portanto ponho o seu length com o valor máximo possível
        int[][] novasEstatisticas = new int[16][9];        
        String[] grupos = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int[] countGrupos = {0, 0, 0, 0, 0, 0, 0, 0}; //cada 0 equivale à qtd de equipas em cada grupo -> string[] em cima
        
        Menu.menu(equipas,novasEquipas,estatisticas,novasEstatisticas,"PracticalWork.csv",rows,linhas,grupos,countGrupos); 
    }  
    
    //1 - Ler a informação disponível no ficheiro de texto (PracticalWork.csv) e armazená-la em memória     
    public static void carregarFicheiro(String[][] equipas, int[][] estatisticas, String ficheiro, int[] rows, String[] grupos, int[] countGrupos) throws FileNotFoundException {
        Scanner ler = new Scanner(new File(ficheiro));        
        ler.nextLine();
        int j = 0;
        
        while (ler.hasNextLine()) {            
            String[] itens = ler.nextLine().split(",");
            equipas[j][0] = itens[0]; //grupo
            equipas[j][1] = itens[1]; //equipa
            for(int i = 2; i < itens.length; i++) { //começa a ler na linha 2. A linha 1 é a legenda
                estatisticas[j][i - 2] = Integer.parseInt(itens[i]);
            }
            j++;
        }
        rows[0] = j;        
        ler.close();
        fillNovoArrayGrupos(equipas,grupos,countGrupos,rows);
        System.out.println("Ficheiro \"PracticalWork.csv\" carregado");
    }
    
    public static void fillNovoArrayGrupos(String[][] equipas, String[] grupos, int[] countGrupos, int[] rows) {
        for(int i = 0; i < grupos.length; i++) {
            for(int j = 0; j < equipas.length; j++) {
                if(grupos[i].equalsIgnoreCase(equipas[j][0])) {
                    countGrupos[i]++;
                }
            }
        }
    }    
        
    //2 - Inserir manualmente info sobre uma seleção. Não permitir equipas com o mesmo nome, nem grupos com nem mais, nem menos de 4 equipas
    public static void inserirGrupo(String[][] equipas, int[] rows, String[] grupos, int[] countGrupos, String grupo) {
        Scanner ler = new Scanner(System.in);
        int a = rows[0];
        String novoGrupo = "";
        boolean repetido = grupoCheio(grupos,countGrupos,grupo);
        int contador = grupoImpossivel(grupos,grupo); //basicamente serve para verificar se o grupo inserido está entre A e H (ou seja, 8 grupos). Se não, ardeu, grupo n interessa, mesmo q tenha menos de 4 elementos
        
        if(repetido == false) {
            equipas[a][0] = grupo.substring(0).toUpperCase(); 
            while(contador > 7) {
                System.out.println("Grupo não permitido. Deves escolher um entre A e H");
                novoGrupo = ler.nextLine();
                equipas[a][0] = novoGrupo.substring(0).toUpperCase();   
                contador = grupoImpossivel(grupos,novoGrupo);
                repetido = grupoCheio(grupos,countGrupos,novoGrupo);
                while(repetido != false) {
                    System.out.println("Grupo repetido! Escolhe outro entre A e H");
                    novoGrupo = ler.nextLine();
                    equipas[a][0] = novoGrupo.substring(0).toUpperCase();   
                    repetido = grupoCheio(grupos,countGrupos,novoGrupo);
                }
            }
                       
            System.out.println("Grupo inserido");
        } else {
            while(repetido != false) {
                System.out.println("Grupo repetido! Insere outro entre A e H");
                novoGrupo = ler.nextLine();
                repetido = grupoCheio(grupos,countGrupos,novoGrupo);
                contador = grupoImpossivel(grupos,novoGrupo); 
                while(contador > 7) {
                    System.out.println("Grupo não permitido. Deves escolher um entre A e H");
                    novoGrupo = ler.nextLine();
                    contador = grupoImpossivel(grupos,novoGrupo);
                    repetido = grupoCheio(grupos,countGrupos,novoGrupo);
                }
            }
            equipas[a][0] = novoGrupo.substring(0).toUpperCase();
            System.out.println("Grupo inserido");
        }
    }

    public static boolean grupoCheio(String[] grupos, int[] countGrupos, String grupo) {
        boolean repetido = false;
        //aqui vou receber o grupo introduzido e testar se já está cheio ou n. estará cheio se countGrupos[i] = 4. aí, repetido = true
        for(int i = 0; i < grupos.length; i++) {
            if(grupo.equalsIgnoreCase(grupos[i])) {
                if(countGrupos[i] >= 4) {
                    repetido = true;
                } else {
                    countGrupos[i]++;
                }
            }
        }
        return repetido;        
    }
    
    public static int grupoImpossivel(String[] grupos, String grupo) {
        int count = 0;
        for(int i = 0; i < grupos.length; i++) {
            if(!grupo.equalsIgnoreCase(grupos[i])) {
                count++;
            }
        }
        return count;
    }
    
    //2 - ADICIONAR EQUIPA
    public static void adicionarGrupoEquipa(String[][] equipas, int[] rows, String grupo, String equipa)  {
        Scanner ler = new Scanner(System.in);
        int a = rows[0];
        String novoElemento = "";          

        novoElemento = equipa;
        boolean repetida = equipaRepetida(equipas,equipa);

        if(repetida == false) {            
            equipas[a][1] = novoElemento.substring(0,1).toUpperCase() + novoElemento.substring(1);
        } else {
            while(equipaRepetida(equipas,novoElemento) != false) {
                System.out.println("Equipa repetida! Escolhe outra");
                novoElemento = ler.nextLine();    
                repetida = equipaRepetida(equipas,novoElemento);
                if(repetida == false) {
                    equipas[a][1] = novoElemento.substring(0,1).toUpperCase() + novoElemento.substring(1);
                    break;
                }
            }
        }
    }
    
    public static boolean equipaRepetida(String equipas[][], String equipaNova) {
        boolean repetida = false;
        for(int i = 0; i < equipas.length; i++) {
            if(equipaNova.equalsIgnoreCase(equipas[i][1])) {
                repetida = true;
            }           
        }        
        return repetida;
    }
    
    public static void adicionarJogos(int[][] estatisticas, int[] rows, int jogos) {
        Scanner ler = new Scanner(System.in);
        int a = rows[0], jgs = jogos;
        
        if(jogos == 3) {
            estatisticas[a][0] = jogos;
        } else {
            while(jgs != 3) {
                System.out.println("As equipas só fazem 3 jogos nesta fase");
                jgs = ler.nextInt();
                if(jgs == 3) {
                    jgs = 3; //acho que esta linha é redundante. este if só entra em execução se jgs = 3
                    estatisticas[a][0] = jgs;
                }
            }
        } 
    }    
    
    public static void adicionarVED(int[][] estatisticas, int[] rows, int vitorias, int empates, int derrotas) {
        Scanner ler = new Scanner(System.in);
        int a = rows[0];
        int v = vitorias, e = empates, d = derrotas, jogos = 3;
        boolean verificaV = verificacao(vitorias), verificaE = verificacao(empates), verificaD = verificacao(derrotas);
        
        if(verificaV == true && verificaE == true && verificaD == true) {
            if(somatorio(vitorias,empates,derrotas)) {
                atribuicaoVED(estatisticas,rows,vitorias,empates,derrotas);
            } else {
                do {
                    System.out.println("O somatório das vitórias, empates e derrotas deve ser igual a 3. Tenta de novo");
                    System.out.println("Vitórias");
                    v = ler.nextInt();
                    System.out.println("Empates");                    
                    e = ler.nextInt();
                    System.out.println("Derrotas");
                    d = ler.nextInt();
                    atribuicaoVED(estatisticas,rows,v,e,d);                   
                } while(somatorio(v,e,d) != true);
            }
        } else {
            do {
                System.out.println("As vitórias devem variar entre 0 e 3");
                System.out.println("Vitórias");
                v = ler.nextInt();
                verificaV = verificacao(v);                              
            } while(verificaV != true);
            
            do {
                System.out.println("Os empate devem variar entre 0 e 3");
                System.out.println("Empates");                    
                e = ler.nextInt();
                verificaE = verificacao(e);
            } while(verificaE != true);
                
            do {
                System.out.println("As derrotas devem variar entre 0 e 3");
                System.out.println("Derrotas");
                d = ler.nextInt();
                verificaD = verificacao(d); 
            } while(verificaD != true);
            
            if(somatorio(v,e,d)) {
                estatisticas[a][1] = v;
                estatisticas[a][2] = e;
                estatisticas[a][3] = d;
            } else {
                do {
                    System.out.println("O somatório das vitórias, empates e derrotas deve ser igual a 3. Tenta de novo");
                    System.out.println("Vitórias");
                    v = ler.nextInt();
                    System.out.println("Empates");                    
                    e = ler.nextInt();
                    System.out.println("Derrotas");
                    d = ler.nextInt();
                    atribuicaoVED(estatisticas,rows,v,e,d);                   
                } while(somatorio(v,e,d) != true);
            }
        } 
    }
    
    public static boolean verificacao(int estatistica) {
        boolean cenas = false;
        
        if(estatistica >= 0 && estatistica <= 3) {
            cenas = true;
        }
        return cenas;
    }
    
    public static boolean somatorio(int vitorias, int empates, int derrotas) {
        boolean sum = false;
        if(vitorias + empates + derrotas == 3) {
            sum = true;
        }
        return sum;
    }
    
    public static void atribuicaoVED(int[][] estatisticas, int[] rows, int vitorias, int empates, int derrotas) {
        int a = rows[0];
        estatisticas[a][1] = vitorias;
        estatisticas[a][2] = empates;
        estatisticas[a][3] = derrotas;
    }
    
    public static void golos(int[][] estatisticas, int[] rows, int gm, int gf) {
        Scanner ler = new Scanner(System.in);
        int a = rows[0], golosMarcados = 0, golosSofridos = 0;
                
        if(maiorDoQueZero(gm)) {
            estatisticas[a][4] = gm;
        } else {
            do {
                System.out.println("Os golos marcados devem ser maiores ou iguais a 0");
                golosMarcados = ler.nextInt();                
            } while(maiorDoQueZero(golosMarcados) == false);
            estatisticas[a][4] = golosMarcados;
        }
        
        if(maiorDoQueZero(gf)) {
            estatisticas[a][5] = gf;
        } else {
            do {
                System.out.println("Os golos sofridos devem ser maiores ou iguais a 0");
                golosSofridos = ler.nextInt();                
            } while(maiorDoQueZero(golosSofridos) == false);
            estatisticas[a][5] = golosSofridos;
        }        
        a++;
        rows[0] = a;
    }
    
    public static boolean maiorDoQueZero(int golos) {
        boolean valor = false;
        
        if(golos >= 0) {
            valor = true;
        }
        return valor;
    }
    
    //3 - Adicionar a coluna de pontos    
    public static void pontosGrupo(String[][] equipas, int[][] estatisticas, int[] rows) {
        for(int i = 0; i < rows[0]; i++) { //coluna 6 = pontos
            estatisticas[i][6] = 3*estatisticas[i][1] + estatisticas[i][2];
            estatisticas[i][7] = estatisticas[i][4] - estatisticas[i][5];
        }        
        
        System.out.println("Colunas de pontos e de diferença de golos adicionadas");
    }
    
    //4 - critérios de desempate
    public static void desempatarGrupos(String[][] equipas, int[][] estatisticas, int[] rows) { //coluna dos pontos: i = 6
        String[] tempString;
        int[] tempInt;        
        
        //organizar por grupos
        for(int i = 0; i < rows[0]; i++) {
            for(int j = i + 1; j < rows[0]; j++) {
                if(equipas[i][0].compareTo(equipas[j][0]) > 0) {
                    tempString = equipas[j];
                    tempInt = estatisticas[j];
                    equipas[j] = equipas[i];
                    estatisticas[j] = estatisticas[i];
                    equipas[i] = tempString;
                    estatisticas[i] = tempInt;
                }
                if (equipas[i][0].compareTo(equipas[j][0]) == 0) {
                    if (estatisticas[i][6] < estatisticas[j][6]) {
                        tempString = equipas[j];
                        tempInt = estatisticas[j];
                        equipas[j] = equipas[i];
                        estatisticas[j] = estatisticas[i];
                        equipas[i] = tempString;
                        estatisticas[i] = tempInt;
                    }
                    if (estatisticas[i][6] == estatisticas[j][6]) {
                        if (estatisticas[i][4] < estatisticas[j][4]) {
                            tempString = equipas[j];
                            tempInt = estatisticas[j];
                            equipas[j] = equipas[i];
                            estatisticas[j] = estatisticas[i];
                            equipas[i] = tempString;
                            estatisticas[i] = tempInt;
                        }
                        if (estatisticas[i][4] == estatisticas[j][4]) {
                            if (estatisticas[i][5] > estatisticas[j][5]) {
                                tempString = equipas[j];
                                tempInt = estatisticas[j];
                                equipas[j] = equipas[i];
                                estatisticas[j] = estatisticas[i];
                                equipas[i] = tempString;
                                estatisticas[i] = tempInt;
                            } 
                            if (estatisticas[i][5] == estatisticas[j][5]) {
                                if (equipas[i][1].compareTo(equipas[j][1]) > 0) {
                                    tempString = equipas[j];
                                    tempInt = estatisticas[j];
                                    equipas[j] = equipas[i];
                                    estatisticas[j] = estatisticas[i];
                                    equipas[i] = tempString;
                                    estatisticas[i] = tempInt;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Ordenação feita");
    }    
    
    public static void adicionarPosicoes(String[][] equipas, int[][] estatisticas, int[] rows) { //entra ao mesmo tempo que a de cima         
        int count = 1;
        
        for(int i = 0; i < rows[0] - 1; i++) {
            if(equipas[i][0].compareTo(equipas[i + 1][0]) == 0) {                
                estatisticas[i][8] = count;
                estatisticas[i + 1][8] = count + 1;
                count++;
            }
            if(equipas[i][0].compareTo(equipas[i + 1][0]) != 0) { //deve dar para meter um "} else {" pelo menos é menos 1 linha
                estatisticas[i][8] = count;
                count = 1;
                estatisticas[i + 1][8] = count;
            }
        }
        
        System.out.println("Posições adicionadas");
    }
        
    //alinea 5    
    public static void listarClassificacao(String[][] equipas, int[][] estatisticas, int[] rows, String[] grupos, int[] countGrupos) {

        System.out.printf("\nListagem geral das equipas\n\n");
        System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
        System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");

        int count = 0;
        for(int i = 0; i < rows[0]; i++) {
            if(estatisticas[i][8] != 0) {
                count++;
                System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6], estatisticas[i][0], estatisticas[i][1], estatisticas[i][2], estatisticas[i][3], estatisticas[i][4], estatisticas[i][5], estatisticas[i][7]);
            }
        }
        
        rows[0] = count;
    }
    
    //alinea 6
    public static void golosMarcadosMaximo(String[][] equipas, int[][] estatisticas, int[] rows) {
        int gm = maximoGolos(estatisticas);
        int i = 0;
        System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
        System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");
        while(i < rows[0]) {
            if(estatisticas[i][4] == gm) {
                System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6], estatisticas[i][0], estatisticas[i][1], estatisticas[i][2], estatisticas[i][3], estatisticas[i][4], estatisticas[i][5], estatisticas[i][7]);
            }
            i++;
        }
    }
    
    public static int maximoGolos(int[][] estatisticas) {
        int max = 0;
        for(int i = 0; i < estatisticas.length; i++) {
            if(estatisticas[i][4] > max) {
                max = estatisticas[i][4];
            }
        }
        return max;
    }
    
    //alinea 7
    public static void equipasMesmosGolosSofridos(String[][] equipas, int[][] estatisticas, int[] rows, int gf) {
        int count = 0; 
        
        for(int i = 0; i < rows[0]; i++) {
            if(gf == estatisticas[i][5]) {
                count++;
            }
        }        
        if(count > 0) {
            System.out.printf("\nListagem das equipas com %d golos sofridos\n", gf);
            System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
            System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");
        
            for(int i = 0; i < rows[0]; i++) {
                if(gf == estatisticas[i][5]) {
                    System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6], estatisticas[i][0], estatisticas[i][1], estatisticas[i][2], estatisticas[i][3], estatisticas[i][4], estatisticas[i][5], estatisticas[i][7]);
                }
            }
        }
        else {
            System.out.printf("\nNão existem equipas com %d golos sofridos\n\n", gf);
        }
    }
    
    //alinea 8
    public static void equipasMaisGolosSofridosDoQueMarcados(String[][] equipas, int[][] estatisticas, int[] rows) {        
        int count = 0;
        int new_count = 0;
        String[] tempString;
        int[] tempInt;
        //tentar fazer sem criar novo array -> Não dará algum trabalho? 
        
        //contagem do numero de equipas que obdece à condição
        for(int i = 0; i < rows[0]; i++) {
            if(estatisticas[i][7] < 0) {
                count++;
            }
        }
        
        //criação de novos arrays
        String[][] new_equipas = new String[count][2];
        int[][] new_estatisticas = new int[count][rows[0]];
        
        //Introdução dos valores nos arrays - a ordem é a do array original
        for(int i = 0; i < rows[0]; i++) {
            if(estatisticas[i][7] < 0) {
                new_equipas[new_count] = equipas[i];
                new_estatisticas[new_count] = estatisticas[i];
                new_count++;
            }
        }
        
        //Organizar alfabeticamente
        for(int i = 0; i < count; i++) {
            for(int j = 0; j < i; j++) {
                if(new_equipas[i][1].compareTo(new_equipas[j][1]) < 0) {
                    tempString = new_equipas[j];
                    tempInt = new_estatisticas[j];
                    new_equipas[j] = new_equipas[i];
                    new_estatisticas[j] = new_estatisticas[i];
                    new_equipas[i] = tempString;
                    new_estatisticas[i] = tempInt;
                }
            }
        }
        
        //listagem
        System.out.printf("\nListagem das equipas com mais golos sofridos que marcados, por ordem alfabetica\n\n");
        System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
        System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");
        
        for(int i = 0; i < count; i++) {
                System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", new_equipas[i][0], new_estatisticas[i][8], new_equipas[i][1], new_estatisticas[i][6], new_estatisticas[i][0], new_estatisticas[i][1], new_estatisticas[i][2], new_estatisticas[i][3], new_estatisticas[i][4], new_estatisticas[i][5], new_estatisticas[i][7]);
        }
    }
    
    //alinea 9
    public static void primeirosClassificados(String[][] equipas, int[][] estatisticas, int[] rows) {
        System.out.printf("\nListagem do primeiro classificado de cada grupo\n\n");
        System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
        System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");
        
        for(int i = 0; i < rows[0]; i++) {
            if(estatisticas[i][8] == 1) {
                System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6], estatisticas[i][0], estatisticas[i][1], estatisticas[i][2], estatisticas[i][3], estatisticas[i][4], estatisticas[i][5], estatisticas[i][7]);
            }
        }
    }
    
    //alinea 10
    public static void listarEquipaEscolhida(String[][] equipas, int[][] estatisticas, String equipaEscolhida, int[] rows) { 
        int count = 0;
        
        for(int i = 0; i < rows[0]; i++) {
            if(!equipaEscolhida.equalsIgnoreCase(equipas[i][1])) {
                count++;
            } else {
                System.out.printf("\nListagem da equipa %s\n\n", equipaEscolhida);
                        System.out.println("| Grp | Pos | Equipa          | Pts| J  | V  | E  | D  | GM | GS | GD |");
                        System.out.println("|=====|=====|=================|====|====|====|====|====|====|====|====|");
                        System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[count][0], estatisticas[count][8], equipas[count][1], estatisticas[count][6], estatisticas[count][0], estatisticas[count][1], estatisticas[count][2], estatisticas[count][3], estatisticas[count][4], estatisticas[count][5], estatisticas[count][7]);
            }
        }
        if(count == rows[0]) {
            System.out.println("Equipa não existe!!");
        }        
    }
    
    //alinea 11
    public static void criarFicheiroStatistics(String[][] equipas, int[][] estatisticas, int[] rows, int[] linhas) throws FileNotFoundException {
        Formatter out = new Formatter(new File("Statistics.txt"));
        newRows(estatisticas,rows,linhas);
        int total_jogos = 0, total_vitorias = 0, total_empates = 0, total_derrotas = 0, total_gm = 0, total_gf = 0;
        
        total_jogos = 6*(linhas[0] / 2);

        for(int i = 0; i < rows[0]; i++) {
            total_vitorias += estatisticas[i][1];
            total_empates += estatisticas[i][2];
            total_derrotas += estatisticas[i][3];
            total_gm += estatisticas[i][4];
            total_gf += estatisticas[i][5];
        }
 
        double media_golos_marcados = (double) total_gm / total_jogos;
        double media_golos_sofridos = (double) total_gf / total_jogos;        
        
        out.format("Total de jogos= %d\n", total_jogos);
        out.format("Total de vitórias= %d\n", total_vitorias);
        out.format("Total de empates= %d\n", total_empates);
        out.format("Total de derrotas= %d\n", total_derrotas);
        out.format("Total de golos marcados= %d\n", total_gm);
        out.format("Total de golos sofridos= %d\n", total_gf);
        out.format("Média de golos marcados por jogo= %.1f\n", media_golos_marcados);
        out.format("Média de golos sofridos por jogo= %.1f\n", media_golos_sofridos);

        out.close();
        System.out.println("Ficheiro \"Statistics.txt\" criado");
    }

    //alinea 12
    public static void novoArray(String[][] equipas, String[][] novasEquipas, int[][] estatisticas, int[][] novasEstatisticas, int[] rows, int[] linhas) { //funciona. vai mostrar só as equipas em 1º e 2º lugar
        newRows(estatisticas,rows,linhas);
        reordenarMatrizes(equipas,estatisticas,rows);
 
        for(int i = 0; i < rows[0]; i++) {
            if(estatisticas[i][8] == 3 || estatisticas[i][8] == 4) {
                for(int j = 0; j < 2; j++) {
                    equipas[i][j] = null;
                }
                for(int h = 0; h < 9; h++) {
                    estatisticas[i][h] = 0;
                }
            }
        }

        //for (int i = 0; i < rows[0]; i++) {
        //    System.out.printf("|%-5s|%5d|%-17s|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%4d|%n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6], estatisticas[i][0], estatisticas[i][1], estatisticas[i][2], estatisticas[i][3], estatisticas[i][4], estatisticas[i][5], estatisticas[i][7]);
        //}
    }
    
    public static void newRows(int[][] estatisticas, int[] rows, int[] linhas) {
        int a = linhas[0]; 
        for(int j = 1; j < rows[0]; j++) {
            if(estatisticas[j-1][8] == 1 && estatisticas[j][8] == 2) {
                a += 2;                
            }            
        }
        linhas[0] = a;
    }
    
    public static void reordenarMatrizes(String[][] equipas, int[][] estatisticas,int[] rows) {
        String[] tempString;
        int[] tempInt;
        
        for (int i = 0; i < rows[0]; i++) {
            for (int j = 0; j < i; j++) {
                if (estatisticas[i][8] < estatisticas[j][8]) {
                    tempString = equipas[j];
                    tempInt = estatisticas[j];
                    equipas[j] = equipas[i];
                    estatisticas [j] = estatisticas[i];
                    equipas[i] = tempString;
                    estatisticas[i] = tempInt;
                }
            }
        }        
    }
    
    public static void eliminarEquipasUnicas(String[][] equipas, int[][] estatisticas, int[] rows, String[] grupos, int[] countGrupos) {
        //modulo chamado na classe menu. é o primeiro a ser chamado
        for(int i = 0; i < countGrupos.length; i++) {
            if(countGrupos[i] == 1) {
                for(int j = 0; j < rows[0]; j++) {
                    if(equipas[j][0].equalsIgnoreCase(grupos[i])) {
                        equipas[j][0] = "cenas";
                        equipas[j][1] = "cenas";
                        estatisticas[j][8] = 3; //posição da equipa unica no grupo passa a 3 p ser eliminada no modulo 12. 
                    }
                }
            }
        }
    }
    
    //alinea 13
    public static void criarFicheiroFinalStage(String[][] equipas, int[][] estatisticas, int[] rows) throws FileNotFoundException {
        Formatter out = new Formatter(new File("FinalStage.csv")); 
        for (int i = 0; i < rows[0] && estatisticas[i][8] != 0; i++) {
            out.format("<Grupo %s>,<Classificação %d>,<%s>,<%d pontos>\n", equipas[i][0], estatisticas[i][8], equipas[i][1], estatisticas[i][6]);
        }
        out.close();
        System.out.println("Ficheiro \"FinalStage.csv\" criado");
    }
    
    //alinea 14 ---------- TEM DE SER CORRIGIDO
    public static void faseSeguinte(String[][] equipas, int[][] estatisticas, int[] rows, int[] linhas) throws FileNotFoundException{ //organizar por grupos é mais fácil do que iterar por char
        ordenarAlfabeticamente(equipas,estatisticas,linhas);       
        Formatter out = new Formatter(new File("FinalStageGames.txt"));        
        int cenas = 0;
        
        for(int i = 0, j = 2, k = 1, m = 3; i < linhas[0] && estatisticas[j][8] != 0; i+=4, j+=4, k+=4,m+=4) {
            out.format("<Grupo %s>,<Classificação %d>,<%s> - <Grupo %s>,<Classificação %d>,<%s>\n", equipas[i][0], estatisticas[i][8], equipas[i][1], equipas[j][0], estatisticas[j][8], equipas[j][1]);
            out.format("<Grupo %s>,<Classificação %d>,<%s> - <Grupo %s>,<Classificação %d>,<%s>\n", equipas[k][0], estatisticas[k][8], equipas[k][1], equipas[m][0], estatisticas[m][8], equipas[m][1]);
        }
        
        out.close();
        System.out.println("Ficheiro \"FinalStageGames.txt\" criado");
    }
    
    public static void ordenarAlfabeticamente(String[][] equipas, int[][] estatisticas, int[] linhas) { //tenho de organizar alfabeticamente e, dentro de equipas do mesmo grupo, pontualmente
        String[] tempString; //grupos 1, 3, 5 é p inverter ordem da ordenação por pontos. ou seja, 2ºs em 1º
        int[] tempInt;        
        
        linhas[0] = linhas[0] / 2;
        
        for(int i = 0; i < linhas[0]; i++) {
            for(int j = i + 1; j < linhas[0]; j++) {//////////////linhas[0]
                if(equipas[i][0].compareTo(equipas[j][0]) > 0) {
                    tempString = equipas[j];
                    tempInt = estatisticas[j];
                    equipas[j] = equipas[i];
                    estatisticas[j] = estatisticas[i];
                    equipas[i] = tempString;
                    estatisticas[i] = tempInt;
                }
                    if (equipas[i][0].compareTo(equipas[j][0]) == 0) {
                        if (estatisticas[i][6] < estatisticas[j][6]) {
                            tempString = equipas[j];
                            tempInt = estatisticas[j];
                            equipas[j] = equipas[i];
                            estatisticas[j] = estatisticas[i];
                            equipas[i] = tempString;
                            estatisticas[i] = tempInt;
                        }
                        if (estatisticas[i][6] == estatisticas[j][6]) {
                            if (estatisticas[i][4] < estatisticas[j][4]) {
                                tempString = equipas[j];
                                tempInt = estatisticas[j];
                                equipas[j] = equipas[i];
                                estatisticas[j] = estatisticas[i];
                                equipas[i] = tempString;
                                estatisticas[i] = tempInt;
                            }
                            if (estatisticas[i][4] == estatisticas[j][4]) {
                                if (estatisticas[i][5] > estatisticas[j][5]) {
                                    tempString = equipas[j];
                                    tempInt = estatisticas[j];
                                    equipas[j] = equipas[i];
                                    estatisticas[j] = estatisticas[i];
                                    equipas[i] = tempString;
                                    estatisticas[i] = tempInt;
                                } 
                                if (estatisticas[i][5] == estatisticas[j][5]) {
                                    if (equipas[i][1].compareTo(equipas[j][1]) > 0) {
                                        tempString = equipas[j];
                                        tempInt = estatisticas[j];
                                        equipas[j] = equipas[i];
                                        estatisticas[j] = estatisticas[i];
                                        equipas[i] = tempString;
                                        estatisticas[i] = tempInt;
                                    }
                                }
                            }
                        }
                }
            }
        }
                
        for(int i = 2, j = i + 1; i < linhas[0]; i += 4, j += 4) {
            tempString = equipas[j];
            tempInt = estatisticas[j];
            equipas[j] = equipas[i];
            estatisticas[j] = estatisticas[i];
            equipas[i] = tempString;
            estatisticas[i] = tempInt;
        }        
    }
}
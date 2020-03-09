package campeonato;
import static campeonato.Campeonato.eliminarEquipasUnicas;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu {
    public static void menu(String[][] equipas, String[][] novasEquipas, int[][] estatisticas, int[][] novasEstatisticas, String ficheiro, int[] rows, int[] linhas, String[] grupos, int[] countGrupos) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        
        String grupo, selecao;
        int jogos = 0, vitorias = 0, empates = 0, derrotas = 0, gm = 0, gf = 0;        
        
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|1 - Ler a informação do ficheiro e armazená-la em memória                                                                                        |");
        System.out.println("|2 - Inserir manualmente informação de uma equipa. Não pode ser repetida                                                                          |");
        System.out.println("|3 - Calcular e armazenar em memória a pontuação de todas as equipas                                                                              |");
        System.out.println("|4 - Calcular e armazenar em memória a classificação de cada equipa                                                                               |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|5 - Listar a classficação das equipas por grupos                                                                                                 |");
        System.out.println("|6 - Listar as equipas cujos golos marcados é igual ao máximo de golos marcados                                                                   |");
        System.out.println("|7 - Listar as equipas com um determinado número de golos sofridos (definido pelo utilizador)                                                     |");
        System.out.println("|8 - Listar as equipas que têm mais golos sofridos do que golos marcados, ordenadas alfabeticamente                                               |");
        System.out.println("|9 - Listar o primeiro classificado de cada grupo                                                                                                 |");
        System.out.println("|10 - Listar informação completa de uma equipa (definida pelo utilizador)                                                                         |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|11 - Criar um ficheiro de texto (Statistics.txt) com estatísticas dos jogos                                                                      |");
        System.out.println("|12 - Remover da memória as equipas que não vão disputar a fase seguinte (3º e 4º classificados de cada grupo)                                    |");
        System.out.println("|13 - Criar um ficheiro de texto (FinalStage.csv) com as equipas que vão disputar a fase seguinte do campeonato                                   |");
        System.out.println("|14 - Criar um ficheiro de texto (FinalStageGames.txt) com os jogos da fase final                                                                 |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|Escolhe uma opção, sabendo que a opção 0 termina o programa                                                                                      |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------------------------|");
        
        int escolha = ler.nextInt();
        ler.nextLine();
        switch(escolha) {
            case 0:
                break;
            case 1: //carrega o ficheiro
                Campeonato.carregarFicheiro(equipas,estatisticas,ficheiro,rows,grupos,countGrupos);                
                suspenderMenu();                
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);              
                break;
            case 2: 
                if (rows[0] < 32) { // -> nao da para adicionar mais equipas
                    System.out.println("Escolhe um grupo. No Campeonato do mundo há 32 equipas com 8 grupos, cada um com 4 equipas. \nUm grupo com 4 elementos está completo. Outro com 1 elemento não pode passar à fase final. O presidente da FIFA não permite!");
                    grupo = ler.nextLine();
                    Campeonato.inserirGrupo(equipas,rows,grupos,countGrupos,grupo);
                    System.out.println("Insere uma equipa");
                    selecao = ler.nextLine();
                    Campeonato.adicionarGrupoEquipa(equipas,rows,grupo,selecao);
                    System.out.println("Quantidade de jogos");
                    jogos = ler.nextInt();
                    Campeonato.adicionarJogos(estatisticas,rows,jogos);
                    ler.nextLine();
                    System.out.println("Vitórias");
                    vitorias = ler.nextInt();
                    System.out.println("Empates");
                    empates = ler.nextInt();
                    System.out.println("Derrotas");
                    derrotas = ler.nextInt();
                    Campeonato.adicionarVED(estatisticas,rows,vitorias,empates,derrotas);
                    System.out.println("Golos marcados");
                    gm = ler.nextInt();
                    System.out.println("Golos sofridos");
                    gf = ler.nextInt();
                    Campeonato.golos(estatisticas,rows,gm,gf);
                    ler.nextLine();
                }
                if (rows[0] == 32) {
                    System.out.println("Grupos completos");
                }
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);   
                break;
            case 3:
                Campeonato.pontosGrupo(equipas,estatisticas,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);   
                break;
            case 4:                
                Campeonato.desempatarGrupos(equipas,estatisticas,rows);
                Campeonato.adicionarPosicoes(equipas,estatisticas,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);    
                break;
            case 5:                
                Campeonato.listarClassificacao(equipas,estatisticas,rows,grupos,countGrupos);                
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);      
                break;
            case 6:
                Campeonato.golosMarcadosMaximo(equipas,estatisticas,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);   
                break;
            case 7:
                System.out.println("Digite um número não negativo para verificar quais as equipas que sofreram esse números de golos:");
                int golosSofridos = ler.nextInt();
                ler.nextLine();
                Campeonato.equipasMesmosGolosSofridos(equipas,estatisticas,rows,golosSofridos);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);  
                break;
            case 8:
                Campeonato.equipasMaisGolosSofridosDoQueMarcados(equipas,estatisticas,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);      
                break;
            case 9:
                Campeonato.primeirosClassificados(equipas,estatisticas,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);     
                break;
            case 10:
                System.out.println("Escolhe uma equipa para veres toda a sua informação classificativa");
                String equipaEscolhida = ler.nextLine();
                Campeonato.listarEquipaEscolhida(equipas,estatisticas,equipaEscolhida,rows);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);        
                break;
            case 11:
                Campeonato.criarFicheiroStatistics(equipas,estatisticas,rows,linhas);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);     
                break;
            case 12:
                Campeonato.eliminarEquipasUnicas(equipas,estatisticas,rows,grupos,countGrupos);
                Campeonato.novoArray(equipas,novasEquipas,estatisticas,novasEstatisticas,rows,linhas);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);       
                break;
            case 13:
                Campeonato.criarFicheiroFinalStage(equipas,estatisticas,linhas);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);       
                break;
            case 14:
                Campeonato.faseSeguinte(equipas,estatisticas,rows,linhas);
                suspenderMenu();
                menu(equipas,novasEquipas,estatisticas,novasEstatisticas,ficheiro,rows,linhas,grupos,countGrupos);    
                break;
        }
    }
    
    public static void suspenderMenu() {
        Scanner ler = new Scanner(System.in);
        System.out.println("Carrega numa tecla para continuar");
        ler.nextLine();
    }        
}
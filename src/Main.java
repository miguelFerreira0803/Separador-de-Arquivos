import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

public class Main {

    public static boolean cabecalho = false;
    public static String TemCabecalho;

    public static long TotalLinhas(String caminho)
    {
        int linhas=0,resultado;
        String texto;

        try{

            FileReader fr = new FileReader(caminho);
            BufferedReader br = new BufferedReader(fr);

            //lê o total de linhas do arquivo até o final
            texto = br.readLine();
            while(texto != null)
            {
                texto = br.readLine();
                linhas++;
            }

        }catch(Exception e) {
            System.out.println("Erro: " + e);
        }

        resultado = JOptionPane.showConfirmDialog(null,"O arquivo possui cabeçalho?");
        if (resultado == JOptionPane.YES_OPTION)
        {
            //retorna o numero de linhas sem contar o cabeçalho
            cabecalho = true;
            return linhas - 1;
        }
        else if (resultado == JOptionPane.NO_OPTION)
            return linhas;
        else
            return -1;
    }

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione um arquivo");
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos permitidos","csv"));

        int resultado = chooser.showOpenDialog(null);
        long TotalLin,partes,qtdPorArq;

        if(resultado == JFileChooser.APPROVE_OPTION)
        {
            File arquivo = chooser.getSelectedFile();
            String caminho = arquivo.getAbsolutePath(); //pega o endereço do arquivo

            try{
                BufferedReader br = new BufferedReader(new FileReader(caminho));

                TotalLin = TotalLinhas(caminho);
                JOptionPane.showMessageDialog(null, "Arquivo escolhido: " + arquivo.getName() + "\nNumero de linhas do arquivo: " + TotalLin);

                if(TotalLin <= 0) {
                    System.out.println("O arquivo está vazio");
                    return;
                }
                else{

                    //pergunta o numero de partes a ser dividido e verifica se é um numero valido
                    partes = Integer.parseInt(JOptionPane.showInputDialog(null, "Quer dividir o arquivo em quantas partes?"));
                    if(partes <=0 || partes > TotalLin)
                    {
                        JOptionPane.showMessageDialog(null, "Numero de partes invalido");
                        return;
                    }
                    else
                    {
                        //define quantas linhas vão ser colocadas em cada arquivo
                        qtdPorArq = TotalLin/partes;

                        //pega o resto das linhas que sobraram, caso aconteça
                        long resto = TotalLin%partes;

                        //case tenha um cabeçalho, faz a leitura da primeira linha
                        if(cabecalho)
                            TemCabecalho = br.readLine();

                        String nomeArq = arquivo.getName().replace("csv","");//define o nome base dos novos arquivos
                        String diretorio = arquivo.getParent(); //define o diretorio onde será gravado as partes

                        for(int i = 0; i < partes; i++)
                        {
                            String NewName = diretorio + File.separator + nomeArq + "_parte" + (i+1) + ".csv"; //da o nome para cada arquivo
                            BufferedWriter bw = new BufferedWriter(new FileWriter(NewName));//cria cada arquivo

                            //escreve o cabeçalho em cada arquivo
                            if(cabecalho)
                            {
                                bw.write(TemCabecalho);
                                bw.newLine();
                            }

                            //escreve as linhas em cada arquivo
                            for(int j = 0; j < qtdPorArq; j++)
                            {
                                String aux = br.readLine();
                                if(aux == null)
                                    break;
                                bw.write(aux);
                                bw.newLine();
                            }
                            bw.close();
                        }
                    }
                }
                br.close();
            }
            catch(Exception e)
            {
                System.out.println("Erro: " + e);
            }
        }
        else
            System.out.println("Nenhum arquivo escolhido");
    }
}
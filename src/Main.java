import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

public class Main {

    public static long TotalLinhas(String caminho)
    {
        int linhas=0,resultado;
        String texto;
        try{
            FileReader fr = new FileReader(caminho);
            BufferedReader br = new BufferedReader(fr);
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
            return linhas - 1;
        else if (resultado == JOptionPane.NO_OPTION)
            return linhas;
        else
            return 0;
    }

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione um arquivo");
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivos permitidos","csv","xlsx"));

        int resultado = chooser.showOpenDialog(null);
        long TotalLin,partes,qtdPorArq;

        if(resultado == JFileChooser.APPROVE_OPTION)
        {
            File arquivo = chooser.getSelectedFile();
            String caminho = arquivo.getAbsolutePath();

            try{
                RandomAccessFile arq = new RandomAccessFile(caminho,"r");
                TotalLin = TotalLinhas(caminho);
                JOptionPane.showMessageDialog(null, "Arquivo escolhido: " + arquivo.getName() + "\nNumero de linhas do arquivo: " + TotalLin);

                String teste = arq.readLine();
                System.out.println(teste);

                if(TotalLin <= 0) {
                    System.out.println("O arquivo está vazio");
                }
                else{
                    partes = Integer.parseInt(JOptionPane.showInputDialog(null, "Quer dividir o arquivo em quantas partes?"));
                    qtdPorArq = TotalLin/partes;
                    for(int i = 0; i < partes; i++)
                    {
                        for(int j = 0; j < qtdPorArq; j++)
                        {

                        }
                    }
                }
                arq.close();
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
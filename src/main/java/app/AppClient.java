/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *
 * @author thepu
 */
public class AppClient {

    
    public static Scanner teclado;
    private Socket clientSocket;
    static String rutacars = "G:\\Steam\\steamapps\\common\\assettocorsa\\content\\cars";
    private static UtilClienteAC util;

    public AppClient(String host, int port) {
        util = new UtilClienteAC();
        try {

            clientSocket = new Socket(host, port);
            System.out.println("Conectado");
            InputStream is = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            util.leerServer(br);
           // int i = 0;
           System.out.println("Elige un numero");
           int opcion = util.leerIntTeclado();
           util.sendSingleLineTextSocket(bw, opcion+"");
           
            //Preparando para recibir fichero
            System.out.println("Preparando para recibir fichero");
            System.out.println("Pausamos 3 segundos de cortesia con el servidor");
            Thread.sleep(3000);
            try{
                DataInputStream din = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                FileOutputStream fou = new FileOutputStream("temp.zip");
                
                int count;
                byte[] buffer = new byte[8192];
                while((count = din.read(buffer))>0){
                    fou.write(buffer,0,count);
                }
                
                fou.flush();
                fou.close();
                din.close();
            
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            System.out.println("Fichero recibido");
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true){}
    }

    public void sendFile(String file) throws IOException {
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
    }

//        private void saveFile(Socket clientSock) throws IOException {
//		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
//		FileOutputStream fos = new FileOutputStream(pathfile);
//		byte[] buffer = new byte[4096];
//		File file = new File(pathfile);
//		int filesize = (int)file.length(); // Send file size in separate msg
//		int read = 0;
//		int totalRead = 0;
//		int remaining = filesize;
//		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
//			totalRead += read;
//			remaining -= read;
//			System.out.println("read " + totalRead + " bytes.");
//			fos.write(buffer, 0, read);
//		}
//		
//		fos.close();
//		dis.close();
//	}
    public static void main(String[] args) {

        teclado = new Scanner(System.in);
        String address = "localhost";
        System.out.println("Iniciando ACSkinClient");
        System.out.println("Elige una opción:\n1.- localhost\n2.- thepuar.ddns.net");
        int opcion = util.leerIntTeclado();

        switch (opcion) {
            case 1:
                address = "localhost";
                break;
            case 2:
                address = "thepuar.ddns.net";
                break;
        }

        AppClient fc = new AppClient(address, 2000);

    }
    
   

}

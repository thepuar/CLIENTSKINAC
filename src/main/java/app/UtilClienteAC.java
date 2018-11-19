/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import app.AppClient;

/**
 *
 * @author thepu
 */
public class UtilClienteAC {

    public void sendListTextSocket(BufferedWriter br, List<String> texto) {
        for (String cadena : texto) {
            sendLineTextSocket(br, cadena);
        }

    }

    public void sendLineTextSocket(BufferedWriter br, String text) {
        text = text.concat("\n");
        try {
            br.write(text);
            br.flush();
        } catch (IOException ioe) {
        }
    }
     public void sendSingleLineTextSocket(BufferedWriter bw, String text) {
        sendLineTextSocket(bw, text);
         sendLineTextSocket(bw, "#");
    }

    public String readLineTextSocket(BufferedReader br) {
        String mensaje = null;
        try {
            while (br.ready() && (mensaje = br.readLine()) != null) {
            }
        } catch (IOException ioe) {
        }
        return mensaje;
    }

    public static int leerIntTeclado() {

        int valor = -1;
        valor = AppClient.teclado.nextInt();
        AppClient.teclado.nextLine();

        return valor;
    }

    public static void leerServer(BufferedReader br) {
        try {
            String mensaje = "";
            while (!mensaje.equals("#")) {
                while (br.ready() && (mensaje = br.readLine()) != null) {
                    if (!mensaje.equals("#")) {
                        System.out.println(mensaje);
                    }
                }
            }
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}

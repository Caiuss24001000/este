import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Menu {

    static String ip = "localhost";

    public static void main(String[] args) throws IOException {
        char opc = ' ';
        String valorParametro = "";
        Scanner leer = new Scanner(System.in);
        while(opc != 'd'){
            System.out.println("MENU\na. Alta suario\nb. Consulta usuario\nc. Borra usuario\nd. Salir\nOpci\u00f3n: ");
            opc = leer.nextLine().charAt(0); //Lectura del primer carácter 

            switch(opc){
                case 'b':
                System.out.println("Ingrese el correo que desea consultar: ");
                valorParametro = leer.nextLine();
                consultar("consulta_usuario", "POST", "email", valorParametro);
                break;

                case 'd':
                    System.out.println("Estamos saliendo :)\n");
                    break;
                default:
                    System.out.println("Esa opci\u00f3n no es valida, intente con otra\n");
            }
        }

        leer.close();
    }

    static void consultar(String tipoConsulta, String metodo, String parametro, String valorParametro) throws IOException{
        URL url = new URL("http://"+ip+":8080/Servicio/rest/ws/"+tipoConsulta);

        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        //True si se va enviar un "body", en este caso el "body" son los parámetros 
        conexion.setDoOutput(true);
        //En este caso usaremos el metodo POST de HTTP
        conexion.setRequestMethod(metodo);
        //Indica que la petición estara codificación como URL 
        conexion.setRequestProperty("Content-Type", "aplication/x-www-form-urlencoded");
        //El metodo web "consulta_usuario" recibe como parametro el email de un usuario, en este caso el email es a@c
        String parametros = parametro+"="+URLEncoder.encode(valorParametro, "UTF-8");

        OutputStream os = conexion.getOutputStream();

        os.write(parametros.getBytes());
        os.flush();

        //Verificamos si hubo un error 
        String respuesta;
        if(conexion.getResponseCode() == 200){
            //No hubo error 
            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            
            //El metodo web regresa una string en formato JSON 
            while((respuesta = br.readLine()) != null)
                System.out.println(respuesta);
        }else {
            //Hubo error 
            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));

            //El metodo web regresa una instancia de la clase error en formato JSON 
            while((respuesta = br.readLine()) != null)
                System.out.println(respuesta);
        }
        conexion.disconnect();
    }
}

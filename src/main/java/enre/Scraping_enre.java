package enre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jni.Time;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import enre.entities.CorteServicioMedia;
import enre.entities.RepoService;
import enre.entities.SinLuzRepositorio;
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.boot.CommandLineRunner;



//@SpringBootApplication(scanBasePackages={"enre","enre.entities"})
@SpringBootApplication()
//@EnableJpaRepositories(basePackages="enre.entities", repositoryFactoryBeanClass = SinLuzRepositorio.class)
public class Scraping_enre implements CommandLineRunner{
	
    public static final String url = "http://www.enre.gov.ar/paginacorte/tabla_cortes_edesur.html";
    public static final String urlDatos = "http://www.enre.gov.ar/paginacorte/js/data_EDS.js";
    public static final int cantidadLecturas = 2000000;
    public static final int maxPages = 20;
    
    @Autowired
	SinLuzRepositorio  repo;
   
    public static void main(String[] args){
		SpringApplication.run(Scraping_enre.class, args);
	}
    
    /*@Bean
	public void demo(SinLuzRepositorioBean repository) {
    	System.out.println("Comprobando entradas de: "+repository.getClass());	
    }*/
    
    @Override
	public void run(String... arg0) throws Exception {
		// clear all record if existed before do the tutorial with new data 
    	System.out.println("Comenzando proceso de captura y guardado de informacion: "+""/*repo.getClass()*/);	
    	capturar_guardar(arg0);
	}
    
    public void capturar_guardar (String args[]) {
    	
        
        for (int i=1; i<cantidadLecturas; i++){
			
            String urlPage = String.format(url, i);
            System.out.println("Comprobando entradas de: "+urlDatos);			
          
			try {
				//leo los datos desde la pagina js
				URL urlPag;
				urlPag = new URL(urlDatos);
				URLConnection con = urlPag.openConnection();
			       
	            BufferedReader in = new BufferedReader(
	               new InputStreamReader(con.getInputStream()));
	       
	            String linea;
	            String mensaje = new String();
	            while ((linea = in.readLine()) != null) {
	               System.out.println(linea);
	               mensaje += linea;
	            }
	            //transformo la variable js en un objeto json
	        	
	            String subCadena = mensaje.substring(10,mensaje.length());
	            String subCadena2 = subCadena.replace("'", "\"");
	            String subCadena3 = subCadena.replace("'", "\"");

	            subCadena3 = subCadena2.replaceAll("\\s","");
	            subCadena3 = subCadena3.replaceAll(",\\{", ";"); //reemplazo momentaneo de ,{
	            subCadena3 = subCadena3.replaceAll("\\{", "\\{\"");
	            subCadena3 = subCadena3.replaceAll(":\"", "\":\"");
	            subCadena3 = subCadena3.replaceAll(":\\[", "\":\\[");
	            subCadena3 = subCadena3.replaceAll(",", ",\""); 
	            subCadena3 = subCadena3.replaceAll(";", ",\\{\"");
	            //en este punto como hay horas hay que volver a dejar las horas sin dobles comillas
	           
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            //deseralizamos el objeto json a un objeto java	            
	            Data data = mapper.readValue(subCadena3, Data.class);	            
	            
	            /*WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
	            HtmlPage page = webClient.getPage(url);
	            System.out.println(page);*/
	            //Document doc = PhantomJsUtils.renderPage(Jsoup.parse(url));
	            
	            //guardamos los datos en la BD
	            CorteServicioMedia item = new CorteServicioMedia(/*(long) 1,*/data.getCortesServicioMedia().get(0).getPartido(),
	            		data.getCortesServicioMedia().get(0).getLocalidad(),
	            		data.getCortesServicioMedia().get(0).getSubestacion_alimentador(),
	            		data.getCortesServicioMedia().get(0).getUsuarios(),
	            		data.getCortesServicioMedia().get(0).getNormalizacion(),
	            		data.getUltimaActualizacion());
	            repo.save(item);
	            
	            //como la pagina se actualiza cada 15 min entonces retrasamos el siguiente proceso
	            //por 15 min
	            System.out.println("Testeado a las..." + new Date());
	            Thread.sleep(10000*6/**15*/);//10 seg  cada 1 minuto
	            
	            
	            String puntodeprueca="";
	            
	            
	            
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            /////////////////
 //           Document datosJS;
//				try {
					//como la pagina carga contenido dinamicamente, los datos se pueden encontrar
					//en un archivo js separado
					//obtengo el archivo de datos
//					datosJS = Jsoup.connect(urlDatos).ignoreContentType(true).get();
					//obtengo solo el objeto json que representa el conjunto de datos
					
					
					
					
//				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			
            
            // Compruebo si me da un 200 al hacer la petición
//            if (getStatusConnectionCode(urlPage) == 200) {
				
                // Obtengo el HTML de la web en un objeto Document2
//                Document document = getHtmlDocument(urlPage);
				
                // Busco todas las historias de meneame que estan dentro de: 
                //Elements entradas = document.select("div.col-md-10.list")/*.not("div.col-md-9.col-xs-12")*/;
//                Elements entradas = document.getElementsByClass("list")/*.not("div.col-md-9.col-xs-12")*/;
				
                // Paseo cada una de las entradas
//                for (Element elem : entradas) {
//                    String partido = elem.getElementsByClass("partido").text();
//                    String localidad = elem.getElementsByClass("localidad").toString();
                    //String fecha = elem.getElementsByClass("fecha").text();
					
//                    System.out.println(partido+"\n"+localidad+"\n");
					
//               }
		
//            }else{
//                System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
//                break;
//            }
        }
    }
    
    public static void main2 (String args[]) {
		
       
        for (int i=1; i<maxPages; i++){
			
            String urlPage = String.format(url, i);
            System.out.println("Comprobando entradas de: "+urlPage);
			
            // Compruebo si me da un 200 al hacer la petición
            if (getStatusConnectionCode(urlPage) == 200) {
				
                // Obtengo el HTML de la web en un objeto Document2
                Document document = getHtmlDocument(urlPage);
				
                // Busco todas las historias de meneame que estan dentro de: 
                //Elements entradas = document.select("div.col-md-10.list")/*.not("div.col-md-9.col-xs-12")*/;
                Elements entradas = document.getElementsByClass("list")/*.not("div.col-md-9.col-xs-12")*/;
				
                // Paseo cada una de las entradas
                for (Element elem : entradas) {
                    String partido = elem.getElementsByClass("partido").text();
                    String localidad = elem.getElementsByClass("localidad").toString();
                    //String fecha = elem.getElementsByClass("fecha").text();
					
                    System.out.println(partido+"\n"+localidad+"\n");
					
                }
		
            }else{
                System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
                break;
            }
        }
    }
	
	
    /**
     * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
     * EJM:
     * 		200 OK			300 Multiple Choices
     * 		301 Moved Permanently	305 Use Proxy
     * 		400 Bad Request		403 Forbidden
     * 		404 Not Found		500 Internal Server Error
     * 		502 Bad Gateway		503 Service Unavailable
     * @param url
     * @return Status Code
     */
    public static int getStatusConnectionCode(String url) {
		
        Response response = null;
		
        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }
	
	
    /**
     * Con este método devuelvo un objeto de la clase Document con el contenido del
     * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
     * @param url
     * @return Documento con el HTML
     */
    public static Document getHtmlDocument(String url) {

        Document doc = null;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }

        return doc;

    }
}

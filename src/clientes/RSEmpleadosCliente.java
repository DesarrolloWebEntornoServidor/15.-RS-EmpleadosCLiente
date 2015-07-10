package clientes;

import java.util.Scanner;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ClientBuilder;

public class RSEmpleadosCliente {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RS-Empleados/webresources";
    //private static final String BASE_URI = "http://192.168.1.16:8080/RS-Empleados/webresources";

    public RSEmpleadosCliente() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.empleados");
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public <T> T findAll_XML(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id})).request(MediaType.APPLICATION_XML).put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id})).request(MediaType.APPLICATION_JSON).put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    public static void main(String[] args) {
        RSEmpleadosCliente c = new RSEmpleadosCliente();
        Empleados emp;
        String emp_json;
        String totalreg;
        String id;
        Scanner in=new Scanner(System.in);
        
        // Calcula el total de registros
        totalreg = c.countREST();
        System.out.println("Servicio Rest RS-Empleados:");
        System.out.println("Número total de registros = " + totalreg);
        
        // Visualiza un empleado por su id         
        do {
            System.out.println("Introduce el id de empleado:");
            id=in.next(); 
            emp=c.find_JSON(Empleados.class, id);  // Devuelve json y se parsea a Empleado
            emp_json=c.find_JSON(String.class, id); // Devuelve json y se deja como cadena
            // Visualización
            if(emp!=null) {
                System.out.println("id=" + emp.getIdempleado());
                System.out.println("Nombre=" + emp.getNombre());
                System.out.println("Telefono=" + emp.getTelefono());
                System.out.println(emp_json); // Se muestra también en formato JSON
            }
            else
                {System.out.println("El empleado no existe");}
        } while (true);
    }
}


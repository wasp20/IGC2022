package proyecto_igc;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import java.util.Scanner;

public class inferencias {
        
          public static Resource obtenerRecurso ( String id , Model model ) {
                    String uri = model.expandPrefix ("cloud:" + id);
                    return model.getResource (uri );
          }

          public static Property obtenerPropiedad ( String id , Model model ) {
                    String uri = model.expandPrefix ("cloud:" + id);
                    return model.getProperty (uri );
          }

          public static void mostrarDeclaraciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto){
       
                    Selector selector = new SimpleSelector(Sujeto, predicado, objeto);
                    StmtIterator iter = inf.listStatements(selector);
                    while (iter.hasNext()){
                        System.out.println(iter.nextStatement().toString());
                    }
          }
          
          public static Boolean bMostrarDeclaraciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto){
                    Boolean hayDeclaraciones = Boolean.FALSE;
                    Selector selector = new SimpleSelector(Sujeto, predicado, objeto);
                    StmtIterator iter = inf.listStatements(selector);
                    while (iter.hasNext()){
                        hayDeclaraciones = Boolean.TRUE;
                        System.out.println(iter.nextStatement().toString());
                    }
                    
                    return hayDeclaraciones;
          }
          
          public static Boolean existenAfirmaciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto) {
                    Boolean hayAfirmaciones;
                    Selector selector = new SimpleSelector(Sujeto, predicado, objeto);
                    StmtIterator iter = inf.listStatements(selector);
                    hayAfirmaciones = iter.hasNext();

                    return hayAfirmaciones;
          }
    
          public static void mostrarDerivaciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto){
        
                    PrintWriter out = new PrintWriter(System.out);
                    for (StmtIterator i = inf.listStatements(Sujeto, predicado, objeto);
                              i.hasNext();){
                              Statement s = i.nextStatement();
                              System.out.println("Statement is " + s);
                              for (Iterator id = inf.getDerivation(s); id.hasNext();){
                                        Derivation deriv = (Derivation) id.next();
                                        deriv.printTrace(out, true);
                               }
                    }
                    out.flush();
          }


          public static void main(String[] args) {

                    System.out.println("Leyendo RDF...");
                    Model model = ModelFactory.createDefaultModel();
                    String inputFileName = "productos_y_servicios_nube.rdf";
                    model.read(inputFileName);
        
                    InfModel inf = ModelFactory.createRDFSModel(model);
                    // Guardar las derivaciones 
                    inf.setDerivationLogging(true);
        
                    String resourceURI = model.expandPrefix("cloud:AmazonRDS");
                    Resource AmazonRDS = model.getResource(resourceURI);
        
                    resourceURI = model.expandPrefix("cloud:ServiciosEnLaNube");
                    Resource ServiciosEnLaNube = model.getResource(resourceURI);
          
    
                    // Inferencias union de conjunto                    
                    if ( existenAfirmaciones (inf,  AmazonRDS, RDF.type, ServiciosEnLaNube )) {
                              System.out.println("La afirmacion es cierta");
                              mostrarDerivaciones(inf, AmazonRDS, RDF.type, ServiciosEnLaNube);
                    } else {
                              System.out.println("La afirmacion NO es cierta ");
                    }


                    //Resource
                    Resource Azure;
                    Resource BD;
                    Resource BDNoRelacional;
                    Resource Datastore;
                    //Propiedad
                    Property alquilar;
                    
                    int option;
                    Scanner myObj = new Scanner(System.in);
                    
                    System.out.println("==========Inferencias=============\n");
                    System.out.println("Ingrese el numero que desea ejecutar\n");
                    System.out.println("[1]Inferencia usando subClassOf");
                    System.out.println("[2]Inferencia usando subPropertyOf");
                    System.out.println("[3]Inferencia usando Domain o Range");
                    System.out.println("[4]Consulta usando patrones de interseccion o union\n");
                    System.out.printf("[Opción]:");                    
                    option = myObj.nextInt();
                    System.out.println("\n");
                    
                    switch(option){
                        case 1:
                            System.out.println("Entrando a la consunta de la inferencia sobre la base de datos DataStore....");
                            String resourcedURI = model.expandPrefix("cloud:BaseDeDatos");
                            BD = model.getResource(resourcedURI);                            
                            Datastore = obtenerRecurso("Datastore", model);

                            if ( existenAfirmaciones (inf,  Datastore, RDF.type, BD )) {
                                      System.out.println("La afirmacion es cierta");
                                      mostrarDerivaciones(inf, Datastore, RDF.type, BD);
                            } else {
                                      System.out.println("La afirmacion NO es cierta ");
}  
                            break;
                        case 2:
                            AmazonRDS = obtenerRecurso("AmazonRDS", model);
                            Azure = obtenerRecurso("MicrosoftAzure", model);
                            alquilar = obtenerPropiedad("alquilarServicios", model);
                            
                            if(bMostrarDeclaraciones(inf, AmazonRDS, alquilar, Azure))
                                System.out.println("La afirmacion es cierta\n");
                            else
                                System.out.println("La afirmacion NO es cierta \n");
                            
                            break;
                            
                        case 3:
                            // Inferencias de tipo domain
                            // :BD :hasKeyValueParadigm "key-value"

                            BD = obtenerRecurso("BaseDeDatos", model);
                            BDNoRelacional = obtenerRecurso("BasesDeDatosNoRelacionales", model);


                            // Inferencias union de conjunto                    
                            if ( existenAfirmaciones (inf,  BD, RDF.type, BDNoRelacional)) {
                                      System.out.println("La afirmacion es cierta");
                                      mostrarDerivaciones(inf,  BD, RDF.type, BDNoRelacional);
                            } else {
                                      System.out.println("La afirmacion NO es cierta ");
                            }
                            break;
                        case 4:
                            AmazonRDS = obtenerRecurso("AmazonRDS", model);
                            ServiciosEnLaNube = obtenerRecurso("ServiciosEnLaNube", model);
                            
                            if ( existenAfirmaciones (inf,  AmazonRDS, RDF.type, ServiciosEnLaNube )) {
                              System.out.println("La afirmacion es cierta");
                              mostrarDerivaciones(inf, AmazonRDS, RDF.type, ServiciosEnLaNube);
                            } else {
                                System.out.println("La afirmacion NO es cierta ");
                            }
                            
                            break;
                        default:
                            System.out.println("Opcion no valida\n");
                            break;
                    }
                    
          }

}

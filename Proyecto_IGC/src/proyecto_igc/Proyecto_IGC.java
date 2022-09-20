
package proyecto_igc;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Proyecto_IGC {

          public static Resource crearRecurso(String uri, String id, Model model){
                    return model.createResource(uri + id);
          }
     
          private static Property crearPropiedad(String uri_base, String id, Model model){
                    return model.createProperty(uri_base, id);
          }
      
          public static void grabarRDF(String nmRDFile, Model model){
              
                    FileOutputStream output = null;
                    try {
                              output = new FileOutputStream(nmRDFile);
                    } catch (FileNotFoundException e) {
                              System.out.println("Ocurrió un error al crear el archivo.");
                    }
                   
                    model.write(output, "RDF/XML-ABBREV");
          }
    
        
          public static void main(String[] args) {
                    Model model = ModelFactory.createDefaultModel();
                    String uri = "http://www.cloud.com/";
                    String ns = "cloud";
                    model.setNsPrefix(ns, uri);
                    
                    
                    Resource ProductosNube = crearRecurso(uri, "ServiciosEnLaNube", model);
                    
                    
                    
                    
// Crear recurso computacion
                    Resource Computacion = crearRecurso(uri, "Computación", model);                
// Hijos de computacion 
                    Resource VM = crearRecurso(uri, "MáquinasVirtuales", model);
                    Resource Contenedores = crearRecurso(uri, "Contenedores", model);
                    Resource ArquitecturaEventos = crearRecurso(uri, "ArquitecturaBasadaEnEventos", model);
          // Hijos de computación
                    // Hijos de computación: Máquinas virtuales
                    Resource NucleoCompartido = crearRecurso(uri, "NúcleoCompartido", model);
                    Resource PropositoGeneral = crearRecurso(uri, "PropósitoGeneral", model);
                    // Hijos de computación: Contenedores
                    Resource Orquestacion = crearRecurso(uri, "OrquestaciónDeContenedores", model);
                    // Hijos de computación:Arquitectura
                    Resource AWSLambda = crearRecurso(uri, "AWSLambda", model);
                    Resource AzureFunctions = crearRecurso(uri, "AzureFunctions", model);
                    Resource CloudFunctions = crearRecurso(uri, "CloudFunctions", model);
                    
// Crear recurso almacenamiento
                    Resource Almacenamiento = crearRecurso(uri, "Almacenamiento", model);
// Hijos de almacenamiento
                    Resource AlmBloque = crearRecurso(uri, "AlmacenamientoEnBloque", model);
                    Resource PersistentDisk = crearRecurso(uri, "PersistentDisk", model);
                    Resource AlmArchivos = crearRecurso(uri, "AlmacenamientoDeArchivos", model);
                    Resource Filestore = crearRecurso(uri, "Filestore", model);
                    Resource AlmObjetos = crearRecurso(uri, "AlmacenamientoDeObjetos", model);
                    Resource CloudStorage = crearRecurso(uri, "CloudStorage", model);
                    
// Crear rcurso base de datos
                    Resource BD = crearRecurso(uri, "BaseDeDatos", model);
// Hijos de base de datos
                    Resource BDRelacional = crearRecurso(uri, "BasesDeDatosRelacionales", model);
                    Resource BDNoRelacional = crearRecurso(uri, "BasesDeDatosNoRelacionales", model);
                    // Hijos de Base de datos relacional
                    Resource AzureSQL = crearRecurso(uri, "AzureSQLDatabase", model);
                    Resource CloudSQL = crearRecurso(uri, "CloudSQL", model);
                    Resource AmazonRDS = crearRecurso(uri, "AmazonRDS", model);
                    // Hijos de Base de datos no relacional
                    Resource AzureDBMaria = crearRecurso(uri, "AzureDataBaseForMariaDB", model);
                    Resource Datastore = crearRecurso(uri, "Datastore", model);
                    Resource AmazonDynamo = crearRecurso(uri, "AmazonDynamoDB", model);
       
// Crear recursos para los proveedores
                    Resource GCP = crearRecurso(uri, "GoogleCloudPlatform", model);
                    Resource AWS = crearRecurso(uri, "AmazonWebServices", model);
                    Resource Azure = crearRecurso(uri, "MicrosoftAzure", model);
                    
// Agregar relaciones entre recursos
// Hijos de Productos en la nube
                    model.add(Computacion, RDFS.subClassOf, ProductosNube);
                    model.add(Almacenamiento, RDFS. subClassOf, ProductosNube);
                    model.add(BD, RDFS.subClassOf, ProductosNube);
                    
          // Hijos de computación
                    model.add(VM, RDFS.subClassOf, Computacion);
                    model.add(Contenedores, RDFS.subClassOf, Computacion);
                    model.add(ArquitecturaEventos, RDFS.subClassOf, Computacion);
                    
          // Hijos de Máquinas virtuales
                    model.add(NucleoCompartido, RDFS.subClassOf, VM);
                    model.add(PropositoGeneral, RDFS.subClassOf, VM);
                    
          // Hijos de contenedores
                    model.add(Orquestacion, RDFS.subClassOf, Contenedores);
                    
          // Hijos de arquitectura basada en eventos
                    model.add(AWSLambda, RDFS.subClassOf, ArquitecturaEventos);
                    model.add(AzureFunctions, RDFS.subClassOf, ArquitecturaEventos);
                    model.add(CloudFunctions, RDFS.subClassOf, ArquitecturaEventos);
                    
           // Hijos de almacenamiento
                    model.add(AlmBloque, RDFS.subClassOf, Almacenamiento);
                    model.add(AlmArchivos, RDFS.subClassOf, Almacenamiento);
                    model.add(AlmObjetos, RDFS.subClassOf, Almacenamiento);
                    
                    model.add(PersistentDisk, RDF.type, AlmBloque);
                    model.add(Filestore, RDF.type, AlmArchivos);
                    model.add(CloudStorage, RDF.type, AlmObjetos);
                    
          // Hijos de bases de datos
                    model.add(BDRelacional, RDFS.subClassOf, BD);
                    model.add(BDNoRelacional, RDFS.subClassOf, BD);
                    
                    // Segun el tipo de base de datos
                    model.add(AzureSQL, RDF.type, BDRelacional);
                    model.add(CloudSQL, RDF.type, BDRelacional);
                    model.add(AmazonRDS, RDF.type, BDRelacional);
                    model.add(AzureDBMaria, RDF.type, BDNoRelacional);
                    model.add(Datastore, RDF.type, BDNoRelacional);
                    model.add(AmazonDynamo, RDF.type, BDNoRelacional);
                    
                    // Por proveedor
                    model.add(AWSLambda, RDF.type, AWS);
                    model.add(AzureFunctions, RDF.type, Azure);
                    model.add(CloudFunctions, RDF.type, GCP);
                    model.add(PersistentDisk, RDF.type, GCP);
                    model.add(Filestore, RDF.type, GCP);
                    model.add(CloudStorage, RDF.type, GCP);
                    
                    model.add(AzureSQL, RDF.type, Azure);
                    model.add(CloudSQL, RDF.type, GCP);
                    model.add(AmazonRDS, RDF.type, AWS);
                    model.add(AzureDBMaria, RDF.type, Azure);
                    model.add(Datastore, RDF.type, GCP);
                    model.add(AmazonDynamo, RDF.type, AWS);
                    
// Crear propiedades y subpropiedades
                    Property ofrecer = crearPropiedad(uri, "ofrecer", model);
                    Property migrarcloud = crearPropiedad(uri, "migrarACloud", model);
                    Property evaluarcostos = crearPropiedad(uri, "evaluarCostos", model);
                    Property vender = crearPropiedad(uri, "venderProductosYServiciosDeTerceros", model);
                    Property venderlicencias = crearPropiedad(uri, "venderLicencias", model);
                    Property vendersuscripciones = crearPropiedad(uri, "venderSuscripciones", model);
                    Property alquilar = crearPropiedad(uri, "alquilarServicios", model);
                    Property alquilarsincosto = crearPropiedad(uri, "alquilarSinCosto", model);
                    Property alquilarconcosto = crearPropiedad(uri, "alquilarConCosto", model);
                    
                    
                    model.add(migrarcloud, RDFS.subPropertyOf, ofrecer);
                    model.add(evaluarcostos, RDFS.subPropertyOf, ofrecer);
                    model.add(vender, RDFS.subPropertyOf, ofrecer);
                    model.add(alquilar, RDFS.subPropertyOf, ofrecer);
                    model.add(alquilarsincosto, RDFS.subPropertyOf, alquilar);
                    model.add(alquilarconcosto, RDFS.subPropertyOf, alquilar);


                    
// Guardar RDF
                    grabarRDF("productos_y_servicios_nube.rdf", model);
                    model.write( System.out , "RDF/XML");
                    
          }
    
}

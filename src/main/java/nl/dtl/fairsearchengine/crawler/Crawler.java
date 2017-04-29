package nl.dtl.fairsearchengine.crawler;

//import static org.junit.Assert.fail;

import org.eclipse.rdf4j.model.URI;
import org.eclipse.rdf4j.model.impl.URIImpl;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
//import org.openrdf.rio.Rio;
//import org.openrdf.rio.UnsupportedRDFormatException;

//import com.github.kburger.revolver.Resolver;
//import com.github.kburger.revolver.ResolverImpl;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.eclipse.rdf4j.model.Model;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Optional;
import java.util.Vector;

import javax.activation.MimeType;

import nl.dtl.fairmetadata4j.io.CatalogMetadataParser;
import nl.dtl.fairmetadata4j.io.CatalogMetadataParserTest;
import nl.dtl.fairmetadata4j.io.DatasetMetadataParser;
import nl.dtl.fairmetadata4j.io.DistributionMetadataParser;
import nl.dtl.fairmetadata4j.io.FDPMetadataParser;
import nl.dtl.fairmetadata4j.io.MetadataParserException;
import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.model.DatasetMetadata;
import nl.dtl.fairmetadata4j.model.DistributionMetadata;
import nl.dtl.fairmetadata4j.model.FDPMetadata;
import nl.dtl.fairmetadata4j.utils.ExampleFilesUtils;
import nl.dtl.fairsearchengine.util.HttpURLConnect;
import nl.dtl.fairsearchengine.util.Theme.Theme;
import nl.dtl.fairsearchengine.util.esClient.JestESClient;
import nl.dtl.fairsearchengine.util.license.SoftwareLicenseCache;

import org.apache.commons.io.IOUtils;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;
import org.json.JSONArray;
import org.json.JSONObject;

//import org.openrdf.model.Statement;
import org.eclipse.rdf4j.model.URI;
import org.eclipse.rdf4j.model.impl.URIImpl;

public class Crawler {
	
	//static String FDPUri = "http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp";
	static String FDPUri = "http://semlab1.liacs.nl:8080/fdp";
	//static String FDPCatalogURI = "http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/biobank";
	//static String FDPCatalogURI = "";
	
	public static void main(String argv[]) throws URISyntaxException{
		//if(argv.length==1)
		//	new Crawler().parse1(FDPUri);
		//else
		//	new Crawler().parse1(argv[1]);
		
	
	
		//return;
		
		Crawler c = new Crawler();
		
		if(argv.length==0)
			c.parse1("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/");
		else
			c.parse1(argv[0]);
		
		//c.parse2();
		
	/*	try {
			if(false)
				c.loadFDP();
		} catch (RDFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedRDFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//c.parse3();
		
		/*Resolver r = new ResolverImpl();
		//Optional o = r.resolve(new java.net.URI("https://rdflicense.appspot.com/rdflicense/cc-by-nc-nd3.0"));
		//Optional o = r.resolve(new java.net.URI("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/"));
		Optional o = r.resolve(new java.net.URI("http://www.w3.org/2000/01/rdf-schema#seeAlso"));
		*/	
	
		
		//System.out.println(o.get());
	} 
	
	public static void parse2(){
		
		
		try {
			IRI iri = new URIImpl("http://dbpedia.org/data/The_Lord_of_the_Rings");
			Theme t = new Theme(iri);
			t.getLabel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	public void parse3(){
		//if(argv.length==1)
		//	new Crawler().parse1(FDPUri);
		//else
		//	new Crawler().parse1(argv[1]);
		String url = "https://rdflicense.appspot.com/rdflicense/cc-by-nc-nd3.0";
		SoftwareLicenseCache slc = new SoftwareLicenseCache(); 
		try {
			URL urlObj = new URL(url);
			slc.SoftwareLicense(urlObj);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			//fail("Malformed URL: " + e.getMessage());
		} catch (Exception e){
			System.err.println("Ops, something went wrong:" + e.getMessage());
		}
		
	}

	
	public void parse4(){
		//if(argv.length==1)
		//	new Crawler().parse1(FDPUri);
		//else
		//	new Crawler().parse1(argv[1]);
		String url = "https://rdflicense.appspot.com/rdflicense/cc-by-nc-nd3.0";
		SoftwareLicenseCache slc = new SoftwareLicenseCache(); 
		try {
			URL urlObj = new URL(url);
			slc.SoftwareLicense(urlObj);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			//fail("Malformed URL: " + e.getMessage());
		} catch (Exception e){
			System.err.println("Ops, something went wrong:" + e.getMessage());
		}
		
	}
	
	private  FDPMetadata loadFDP(String uri) throws RDFParseException, UnsupportedRDFormatException, IOException {
		
		HttpURLConnect con = new HttpURLConnect();
		 
		String data = null;
		
		try {
			data = con.sendGet(uri);
			System.out.println(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			  
		  
	    FDPMetadataParser fdpMetadataParser = new FDPMetadataParser();
	    FDPMetadata fdpMetadata = null;
		try {
			fdpMetadata = fdpMetadataParser.parse(data, new URIImpl(uri), RDFFormat.TURTLE);
		} catch (MetadataParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	               
	    //mudar nome de variavel
	    //TODO unccoment x 4
	    
	      

	    //List<URI> catalogURI2 = new Vector();
	      
	     // catalogURI2.add(new URIImpl("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/biobank"));
	     // catalogURI2.add(new URIImpl("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/comparativeGenomics"));
	     // catalogURI2.add(new URIImpl("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/patient-registry"));
	     // catalogURI2.add(new URIImpl("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/textmining"));
	     // catalogURI2.add(new URIImpl("http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/transcriptomics"));
	      
	     // catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank"));
	     // catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/registry"));
	      	       
		/*
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/173631-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/44001-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/45274-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/45401-collection1"));
	  
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/45401-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/62316-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/76957-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77088-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77219-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77350-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77489-collection1"));
	       
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77630-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/77761-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/87919-collection1"));
	       catalogURI2.add(new URIImpl("http://semlab1.liacs.nl:8080/fdp/biobank/88051-collection1")); */
	       
	       return fdpMetadata;
	}
	
	public void parse1(String uri){
		
		
	CatalogMetadata catalogMetadata = null;
	DatasetMetadata datasetMetadata = null;
	DistributionMetadata distributionMetdada = null;
	
	List<String> jsonList = new Vector();
	     
     int ci = 0,  di  = 0 , dii = 0;
	 List<IRI> catalogList;
			try {
				FDPMetadata fdpmetadata = this.loadFDP(uri);
				
			    List<IRI> catalogURI = fdpmetadata.getCatalogs();
			    List<IRI> catalogURI2 = new ArrayList();
			      
			    catalogList = new Vector();
			    
			    for(int i = 0; i < catalogURI.size(); i++){
			    	  System.out.println("Identified catalogs " + catalogURI.get(i).stringValue());
			    	  catalogList.add(catalogURI.get(i));
			    }
			      
				
				for(IRI ctURI : catalogList){
    
		    	   try{
		    	   	  catalogMetadata = this.doCatalogMetadataParser(ctURI);
		    	   	  setupJSON(fdpmetadata, catalogMetadata, datasetMetadata, distributionMetdada, ctURI, jsonList);
		    	   } catch(IOException | MetadataParserException | MimeTypeException e){
		    		   
		    	   }			
				}
	       
		} catch (RDFParseException | UnsupportedRDFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       
	       
	    HttpURLConnect httpCon = new HttpURLConnect();
	    int i = 0;
	    for(String jsonElem: jsonList){
	    	String data = "{ \"create\": { \"_index\": \"dataset\", \"_type\": \"dataset\"}}\n";
	    	data += jsonElem + "\n";
	    	
	    	//System.out.println("{ \"create\": { \"_index\": \"dataset\", \"_type\": \"dataset\"}}" );
	    	//System.out.println(jsonElem);	
	    	
	    	try {
	    			httpCon.sendPost("http://127.0.0.1:9200/_bulk", data);
	    			//httpCon.sendPost("http://145.100.59.75/_bulk", data);
	    			//System.out.println(data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
	    }
    
	}

	private void setupJSON(FDPMetadata fdpmetadata, CatalogMetadata catalogMetadata, DatasetMetadata datasetMetadata, DistributionMetadata distributionMetdada, URI ctURI, List jsonList) throws MalformedURLException, MetadataParserException, IOException, MimeTypeException{
		List<IRI> dataset = catalogMetadata.getDatasets();
		//System.out.println("> "+ci++ + " " + ctURI.toString() + " " + dataset.size());
		
		for(URI uri : dataset){
			//System.out.println(">> "+di++);
			datasetMetadata = this.doDatasetMetadaParser(uri);
			List<IRI> distribution = datasetMetadata.getDistributions();
				JSONObject es = new JSONObject();
				
				//if(catalogMetadata.getHomepage()!=null) es.put("latndingPage");
				if(datasetMetadata.getTitle()!=null) es.put("title", datasetMetadata.getTitle().stringValue());
				if(datasetMetadata.getDescription()!=null) es.put("description", datasetMetadata.getDescription().stringValue());
				if(catalogMetadata.getTitle()!=null) es.put("catalogTitle", catalogMetadata.getTitle().stringValue());
				if(datasetMetadata.getLandingPage()!=null) es.put("landingPage", datasetMetadata.getLandingPage());
				
				IRI institutionCountry  = fdpmetadata.getInstitutionCountry();
				List<IRI> catalogThemes = datasetMetadata.getThemes();
				
				
				es.put("repositoryTitle", "DTL FAIR Data Point");
				
				es.put("repositoryCountry", "NL"); //TODO improve
				
				//es.put("description_suggest", datasetMetadata.getDescription().stringValue() );
				
				//es.put("suggest", new JSONObject().put("input", datasetMetadata.getDescription().stringValue() ));
				List<String> suggest = new Vector();
				
				JestESClient jec = new JestESClient();
				List<String> tokenList = jec.doTextAnalysis("english", datasetMetadata.getTitle().stringValue());
				
				for(int i = 0; i < tokenList.size(); i++){
					System.out.println(tokenList.get(i) );
					suggest.add( tokenList.get(i) );
				}
				
				
				es.put("suggest", new JSONObject().put("input", suggest ));

				
				JSONArray coords = new JSONArray();
					coords.put(new Double(52.13263));
					coords.put(new Double(5.29126));
				es.put("repositoryLocation", coords);
				
				List<IRI> taxonomyList = catalogMetadata.getThemeTaxonomys();
				List<String> taxonomyStringList = new Vector();
				
				for(URI taxonomyUri: taxonomyList){ //adicionar
					taxonomyStringList.add(taxonomyUri.stringValue());
				}
				
				es.put("taxonomyList", taxonomyStringList);
				

				List<Map> distributionList = new Vector();
						
				for(URI distributionUri : distribution){
						   Map<String,String> distributionMap = new HashMap();
						   //System.out.println(">>> "+dii++);
						   distributionMetdada = this.doDistributionMetadataParser(distributionUri);
						   
						   if(distributionMetdada.getTitle()!=null)
						     distributionMap.put("title", distributionMetdada.getTitle().stringValue());
						   if(distributionMetdada.getDownloadURL()!=null)
						     distributionMap.put("download",    distributionMetdada.getDownloadURL().stringValue());

						   if(distributionMetdada.getLicense()!=null){
							   distributionMap.put("licenseURI", distributionMetdada.getLicense().stringValue());
							  // if(Crawler.convertLicenseURIToAcronym(distributionMetdada.getLicense().stringValue())!=null)
							  // distributionMap.put("licenseAcronym", Crawler.convertLicenseURIToAcronym(distributionMetdada.getLicense().stringValue()));
							   distributionMap.put("licenseAcronym", "APACHE");
						   }
						   
				
						   
						   
						  // distributionMetdada.
						   
						   if(distributionMetdada.getDownloadURL()!=null){
						   		int size = this.getFileSize(new URL( distributionMetdada.getDownloadURL().stringValue() ));
						   		//int size = this.getFileSize(new URL( "http://www.sapo.pt/" ));
						   		System.out.println("********* ******* ******** SIZE: "+size);
						   		distributionMap.put( "downloadSize", size + "" );
						   		distributionMap.put( "downloadHumanReadableSize", Crawler.humanReadableByteCount(size, false) );
						   }
						   
						   
						   if(distributionMetdada.getAccessURL()!=null)
						     distributionMap.put("accessURL",   distributionMetdada.getAccessURL().stringValue());
						   if(distributionMetdada.getFormat()!=null)
							 distributionMap.put("format", distributionMetdada.getFormat().stringValue());
						   else{
							   if(distributionMetdada.getMediaType()!=null && distributionMetdada.getFormat()==null){
								   String sourceMymeType = distributionMetdada.getMediaType().stringValue();
								   MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
								   //TODO treat exception here
								   org.apache.tika.mime.MimeType mimeType = allTypes.forName(sourceMymeType);
								   String fileExtension = mimeType.getExtension();
								   distributionMap.put("format", fileExtension);
							   } else distributionMap.put("format", "unknown");
						   }
						    // distributionMap.put("xxx", "xxxxxx");
						   
						   distributionList.add(distributionMap);
				}	
				es.put("distribution", distributionList);
				
				//improve structure
				es.put("FDPurl", FDPUri.toString());
				es.put("catalogURL", ctURI);
				es.put("datasetURL", uri);
				
				//jsonList.add("datasetURL")
				
				System.out.println("JSON: "+es.toString());
				jsonList.add(es.toString());
		}
	}
	
	private int getFileSize(URL url) {
	    HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLength();
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	        return -1;
	    } finally {
	        conn.disconnect();
	    }
	}
	
	private DistributionMetadata doDistributionMetadataParser(URI distributionUri) throws MalformedURLException, IOException, MetadataParserException {
		//System.out.println("=== "+distributionUri.stringValue());
		
		DistributionMetadataParser disMP = new DistributionMetadataParser();
		InputStream in2 = new URL(distributionUri.stringValue()).openStream();
		DistributionMetadata distributionMetadata = disMP.parse(IOUtils.toString(in2), new URIImpl(distributionUri.toString()), RDFFormat.TURTLE );
	
		//System.out.println("======Des "+distributionMetadata.getDescription());
		//System.out.println("======Dow "+distributionMetadata.getDownloadURL());
		//System.out.println("======Acc "+distributionMetadata.getAccessURL());
		
		
		return distributionMetadata;
		//System.out.println("json> " + es.toString());
	}

	private DatasetMetadata doDatasetMetadaParser(URI uri) throws MalformedURLException, IOException, MetadataParserException {
		System.out.println(uri.stringValue());
		DatasetMetadataParser dmp = new DatasetMetadataParser();
		InputStream in = new URL( uri.stringValue() ).openStream();
		DatasetMetadata dm = dmp.parse(IOUtils.toString(in), new URIImpl(uri.toString()), RDFFormat.TURTLE);
		//System.out.println("-"+dm.getDescription());
		//System.out.println("-"+dm.getTitle());
		//System.out.println("-"+dm.getDescription());
		
		return dm;
				
	}

	public CatalogMetadata doCatalogMetadataParser(IRI ctURI) throws MetadataParserException, MalformedURLException, IOException{
		 //System.out.println("-> "+ctURI.toString());
		   
	       CatalogMetadataParser parser = new CatalogMetadataParser();	
	       
	       InputStream in;
	       //in = new URL( "http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/biobank" ).openStream();
	       in = new URL( ctURI.toString() ).openStream();
	        
	        IRI cURI = ctURI;
	        IRI fURI = new URIImpl(FDPUri);
	        String rdf = IOUtils.toString(in);
	        //System.out.println(rdf);
	        CatalogMetadata metadata = parser.parse(
	                 rdf, cURI, fURI, 
	                RDFFormat.TURTLE);
	        
	        
	 
	        
	        return metadata;
	}
	
	
    public static String getFileContentAsString(String fileName)  {        
        String content = "";  
        try {
            URL fileURL = Crawler.class.getResource(fileName);
            content = Resources.toString(fileURL, Charsets.UTF_8);
        } catch (IOException ex) {
            //LOGGER.error("Error getting turle file",ex);          
        }        
        return content;
    } 
    
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
    
    public static String convertLicenseURIToAcronym(String uri){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	map.put("https://creativecommons.org/licenses/by-nc-nd/3.0/", "CC BY-NC-ND 3.0");
    	
    	return map.get(uri);
		    	    	
    }
    
	
}
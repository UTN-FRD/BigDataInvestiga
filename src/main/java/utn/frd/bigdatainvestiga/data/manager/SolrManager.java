/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.data.manager;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import utn.frd.bigdatainvestiga.util.PropertyUtils;

/**
 *
 * @author Sergio
 */
public class SolrManager {
    
    private static SolrManager instance = null;
    
    private SolrClient solr;
    
    private SolrManager(){
        String urlString = PropertyUtils.get("solr.url"); // "http://127.0.0.1:8983/solr/comunicaciones";
        solr = new HttpSolrClient.Builder(urlString).build();
    }
    
    public static SolrClient getSolrClient(){
        if(instance==null){
            instance = new SolrManager();
        }
        return instance.solr;
    }
    
}

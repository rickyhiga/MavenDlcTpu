/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class DriveCommandLine {
//
//  private static String CLIENT_ID = "62144146491-40q4psnohqrq12ce5i8607fvct9dos3l.apps.googleusercontent.com";
//  private static String CLIENT_SECRET = "puNShA07pFElg7CL-OfxFh_W";
//
//  private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
//  
//  public static void main(String[] args) throws IOException {
//    HttpTransport httpTransport = new NetHttpTransport();
//    JsonFactory jsonFactory = new JacksonFactory();
//   
//    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
//        .setAccessType("online")
//        .setApprovalPrompt("auto").build();
//    
//    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
//    System.out.println("Please open the following URL in your browser then type the authorization code:");
//    System.out.println("  " + url);
//    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    String code = br.readLine();
//    
//    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
//    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
//    
//    //Create a new authorized API client
//    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
//
//    //Insert a file  
//    File body = new File();
//    body.setTitle("My document");
//    body.setDescription("A test document");
//    body.setMimeType("text/plain");
//    
//    java.io.File fileContent = new java.io.File("C:\\Users\\user\\Documents\\RickyFacu\\2015\\DLC\\TPU\\MavenDlcTpu\\document.txt");
//    FileContent mediaContent = new FileContent("text/plain", fileContent);
//
//    File file = service.files().insert(body, mediaContent).execute();
//    System.out.println("File ID: " + file.getId());
//  }
}
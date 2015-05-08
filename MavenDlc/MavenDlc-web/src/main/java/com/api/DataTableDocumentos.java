/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import beans.DocumentoBean;
import java.util.List;

/**
 *
 * @author Nico
 */
public class DataTableDocumentos {

 int  iTotalRecords;
 int  iTotalDisplayRecords;
 String  sEcho;
 String sColumns;
 List<DocumentoBean> aaData;

 public int getiTotalRecords() {
  return iTotalRecords;
 }

 public void setiTotalRecords(int iTotalRecords) {
  this.iTotalRecords = iTotalRecords;
 }

 public int getiTotalDisplayRecords() {
  return iTotalDisplayRecords;
 }

 

 public void setiTotalDisplayRecords(int iTotalDisplayRecords) {

  this.iTotalDisplayRecords = iTotalDisplayRecords;

 }

 

 public String getsEcho() {
  return sEcho;
 }
 
 public void setsEcho(String sEcho) {
  this.sEcho = sEcho;

 }

 

 public String getsColumns() {

  return sColumns;

 }


 public void setsColumns(String sColumns) {

  this.sColumns = sColumns;

 }

 

 public List<DocumentoBean> getAaData() {

  return aaData;

 }

 

 public void setAaData(List<DocumentoBean> aaData) {
  this.aaData = aaData;
 }

  

}

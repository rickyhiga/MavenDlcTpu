$(document).ready(function(){
 
    oTableDoc = $('#table_documentos_res').dataTable({
        "bProcessing": true,
        "bServerSide": true,
        "bPaginate"  : false,
        "bFilter": false,
        "bInfo" : false,
        "bLengthChange": false,
        "iDisplayLength":25,
        "sAjaxSource": "/MavenDlc-web/faces/listResultados.xhtml",
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            $.getJSON( sSource, aoData, function (json) {
                fnCallback(json);
            });
        } ,
        "aoColumnDefs": [
            {
                "sName": "nombre",
                "mData": "nombre",
                "sTitle": "Nombre",
                "aTargets": [0]
            },
            {
                "sName": "url",
                "mData": "url",
                "sTitle": "URL",
                "aTargets": [1]
            }
            ]
    });
    
      
    
});
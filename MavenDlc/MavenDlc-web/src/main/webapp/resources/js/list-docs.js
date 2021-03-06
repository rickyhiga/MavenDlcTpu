$(document).ready(function(){
 
    oTableDoc = $('#table_documentos_res').dataTable({
        "bProcessing": true,
        "bServerSide": true,
        "bPaginate"  : false,
        "bFilter": false,
        "bInfo" : false,
        "bLengthChange": false,
        "iDisplayLength":10,
        "sAjaxSource": "listResultados.xhtml",
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            $.getJSON( sSource, aoData, function (json) {
                fnCallback(json);
            });
        } ,
        "aoColumnDefs": [
            {
                "sName": "order",
                "mData": "order",
                "sTitle": "#",
                "aTargets": [0]
            },
            {
                "sName": "nombre",
                "mData": "nombre",
                "sTitle": "Nombre",
                "aTargets": [1]
            },
            {
                "sName": "url",
                "mData": "url",
                "sTitle": "URL",
                "aTargets": [2]
            },
            {
                "sName": "puntosRank",
                "mData": "puntosRank",
                "sTitle": "Valoracion",
                "aTargets": [3]
            },
            {
                "sName": "Actions",
                "sTitle": "Acciones",
                "sClass": "td-actions",
                "mData": "id",
                "bSortable": false,
                "aTargets": [4],
                "mRender": function (data, type, full) {
                    var returnButton = '<div class="btn-group">';
                    
                        returnButton += '<a href="mostrarDoc.xhtml?url=' + full.url + '" class="btn btn-mini btn-success">' +
                                                '<i class="fa fa-list bigger-120"></i> Ver Documento' +
                                            '</a>';
		
                        returnButton += '</div>';
                    return returnButton;
                }
            }
            ]
    });
    
      
    
});
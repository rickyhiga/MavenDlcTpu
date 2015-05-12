/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var wsUri = "ws://" + document.location.host + "/MavenDlc-web/mediatorendpoint";
var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

// For testing purposes
var output = document.getElementById("output");
websocket.onopen = function(evt) { onOpen(evt) };

function writeToScreen(message) {
    //output.innerHTML += message + "<br>";
    
   
     var array = message.toString().split("#");
     if(array.length>0){
         playSound("wa");
        $('#cant_indexados').html(parseInt($('#cant_indexados').html())+1);
        $( ".lv-body" ).append('<a class="lv-item" href=""><div class="media"><div class="media-body"><div class="lv-title">'+array[0]+'</div> <small class="lv-small">'+array[1]+'</small></div></div></a>');
    }
}

function onOpen() {
    //writeToScreen("Connected to " + wsUri);
    console.log("Connected to " + wsUri);
}
// End test functions

websocket.onmessage = function(evt) { onMessage(evt) };

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}
                
function onMessage(evt) {
    console.log("received: " + evt.data);
    writeToScreen(evt.data);
}

    // @param filename The name of the file WITHOUT ending
    function playSound(filename){   
                document.getElementById("sound").innerHTML='<audio autoplay="autoplay"><source src="' + filename + '.mp3" type="audio/mpeg" /><source src="' + filename + '.ogg" type="audio/ogg" /><embed hidden="true" autostart="true" loop="false" src="' + filename +'.mp3" /></audio>';
            }

delete FROM buscadordlc.posteo;
delete FROM buscadordlc.documento;
delete FROM buscadordlc.vocabulario;
ALTER TABLE `buscadordlc`.`vocabulario` 
AUTO_INCREMENT = 1 ;
ALTER TABLE `buscadordlc`.`posteo` 
AUTO_INCREMENT = 1 ;
ALTER TABLE `buscadordlc`.`documento` 
AUTO_INCREMENT = 1 ;

/* global malarkey:false, moment:false */
(function() {
    'use strict';
  
    angular
      .module('spapp')
      .constant('malarkey', malarkey)
      .constant('moment', moment)
      .constant('API_CONFIG', {
 
	 //   	'url' : 'http://10.1.1.170:8680/oefa-sinada-web/rest/api/' ,
     	'url' : 'http://localhost:8083/oefa-sinada-web/rest/api/',
    	//  'url' : 'http://172.17.17.112/oefa-sinada-web/rest/api/',	
          
      //    'url' : 'http://10.1.1.162/oefa-sinada-web/rest/api/',
  //       'url' : 'http://localhost:8050/oefa-sinada-web/rest/api/',
 //        'urlSSO' : 'http://srvssoqa.oefa.gob.pe/OEFA.STS',
          // 'urlSSO' : 'https://desarrollo.oefa.gob.pe/OEFA.STS',
		 'urlSSO' : 'https://desarrollo.oefa.gob.pe/OEFA.STS',
     //   'url' : 'https://sistemas.oefa.gob.pe/oefa-sinada-web/rest/api/',
     //	 'urlSSO' : 'https://sso.oefa.gob.pe/OEFA.STS',
         'urlFeatureService': 'https://pifa.oefa.gob.pe/arcgis/rest/services/APP_SINADA/SERV_LOC_DENUNCIA_SINADA_DES/FeatureServer/0',
          'urlArcGisProxy': 'https://pifa.oefa.gob.pe/DotNet/proxy.ashx',
          // CALIDAD
  
          //'idAplicacion'          : 7,  // Ingresar el id de la aplicacion registrada en el sso
  
          // registrar el id del perfil registrado en el sso
          //'idPerfilDenunciante'   : 14,
          //'idPerfilEspecialista'  : 11,
          //'idPerfilCoordinador'   : 12,
          //'idPerfilEntidad'       : 13,
          
          // registrar el id de la opciones registrada en el sso
          //'idMantenimientoMaestro'            : 70,
          //'idCasosDenuncias'                : 71,
          //'idDenuncianteBandejaDenuncias'   : 72,
          //'idAsignacionBandejaDenuncias'    : 73,
          //'idEspecialistaBandejaDenuncias'  : 74,
          //'idEntidadBandejaDenuncias'       : 75
            
         
        //    PRODUCCION
        
          'idAplicacion' 			: 42,  // Ingresar el id de la aplicacion registrada en el sso
  
          
          // registrar el id del perfil registrado en el sso
        'idPerfilDenunciante' 	: 45,  
        'idPerfilEspecialista' 	: 42,
        'idPerfilCoordinador' 	: 43,
        'idPerfilEntidad' 		: 44,
  //        
  //        // registrar el id de la opciones registrada en el sso
        'idMantenimientoMaestro' 			: 79,
        'idCasosDenuncias'                : 82,
        'idDenuncianteBandejaDenuncias'   : 84,
        'idAsignacionBandejaDenuncias'    : 85,
        'idEspecialistaBandejaDenuncias'  : 86,
        'idEntidadBandejaDenuncias'       : 87
      })
      .constant('EXTERNAL_HOME_PAGE', {
          'state' : 'spapp-portal.main.eventos',
          'url' : '/portal/eventos'
      })
      .constant('INTERNAL_HOME_PAGE', {
          'state' : 'spapp.home.inicio',
          'url' : '/home/inicio'
      })
      .constant('LOGIN_PAGE', {
          'state' : 'ingreso',
          'url' : '/ingreso'
      })
      .constant('ARCGIS_CONFIG', {
          mapLayers: [
              {
                  title: 'Minería',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_MINERIA_MFICAM/MapServer'
              },
              {
                  title: 'Hidrocarburos Líquidos',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_HID_MFICAM/MapServer'
              },
              {
                  title: 'Electricidad',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_ELEC_MFICAM/MapServer'
              },
              {
                  title: 'Industria',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_IND_MFICAM/MapServer'
              },
              {
                  title: 'Gas Natural',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_GN_MFICAM/MapServer'
              },
              {
                  title: 'Pesquería',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_PESQ_MFICAM/MapServer'
              },
              {
                  title: 'Entidades de Fiscalización Ambiental',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_EFAS_MFICAM/MapServer'
              },
              {
                  title: 'Cartografia Base',
                  url: 'https://pifa.oefa.gob.pe/arcgis/rest/services/PORTAL/SERV_BASE_MFICAM/MapServer',
                  visible: true,
              },
          ]
      });
  
  })();

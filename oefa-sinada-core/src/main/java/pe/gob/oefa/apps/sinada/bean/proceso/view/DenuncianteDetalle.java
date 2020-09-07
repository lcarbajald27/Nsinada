package pe.gob.oefa.apps.sinada.bean.proceso.view;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class DenuncianteDetalle extends BaseBean{

		private long 	idDenunciante;
		private long 	idDenuncia;
		private int 	tipoPersona;
		private String 	nombreTipoPersona;
		private long 	idPersona;
		private String 	numeroDocumento;
		private String 	nombreCompleto;
		private String 	direccion;
		private String 	referencia;
		private long 	representanteLegal;
		private String 	documentoRepresentante;
		private String 	nombreRepresentante;
		private int 	cargo;
		private String 	nombreCargo;
		private int 	estado;
		
		
		public long getIdDenunciante() {
			return idDenunciante;
		}
		public void setIdDenunciante(long idDenunciante) {
			this.idDenunciante = idDenunciante;
		}
		public long getIdDenuncia() {
			return idDenuncia;
		}
		public void setIdDenuncia(long idDenuncia) {
			this.idDenuncia = idDenuncia;
		}
		public int getTipoPersona() {
			return tipoPersona;
		}
		public void setTipoPersona(int tipoPersona) {
			this.tipoPersona = tipoPersona;
		}
		public String getNombreTipoPersona() {
			return nombreTipoPersona;
		}
		public void setNombreTipoPersona(String nombreTipoPersona) {
			this.nombreTipoPersona = nombreTipoPersona;
		}
		public long getIdPersona() {
			return idPersona;
		}
		public void setIdPersona(long idPersona) {
			this.idPersona = idPersona;
		}
		public String getNumeroDocumento() {
			return numeroDocumento;
		}
		public void setNumeroDocumento(String numeroDocumento) {
			this.numeroDocumento = numeroDocumento;
		}
		public String getNombreCompleto() {
			return nombreCompleto;
		}
		public void setNombreCompleto(String nombreCompleto) {
			this.nombreCompleto = nombreCompleto;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getReferencia() {
			return referencia;
		}
		public void setReferencia(String referencia) {
			this.referencia = referencia;
		}
		public long getRepresentanteLegal() {
			return representanteLegal;
		}
		public void setRepresentanteLegal(long representanteLegal) {
			this.representanteLegal = representanteLegal;
		}
		public String getDocumentoRepresentante() {
			return documentoRepresentante;
		}
		public void setDocumentoRepresentante(String documentoRepresentante) {
			this.documentoRepresentante = documentoRepresentante;
		}
		public String getNombreRepresentante() {
			return nombreRepresentante;
		}
		public void setNombreRepresentante(String nombreRepresentante) {
			this.nombreRepresentante = nombreRepresentante;
		}
		public int getCargo() {
			return cargo;
		}
		public void setCargo(int cargo) {
			this.cargo = cargo;
		}
		public String getNombreCargo() {
			return nombreCargo;
		}
		public void setNombreCargo(String nombreCargo) {
			this.nombreCargo = nombreCargo;
		}
		public int getEstado() {
			return estado;
		}
		public void setEstado(int estado) {
			this.estado = estado;
		}
		

}

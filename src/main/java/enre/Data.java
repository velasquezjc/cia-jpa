package enre;

import java.util.List;

public class Data {
		private String fuente;
		private String empresa;
		private String totalUsuariosSinSuministro;
		private String totalUsuariosConSuministro;
		private String ultimaActualizacion;
		private String totalUsuariosAyer;
		private List<ItemCortesServicioMedia> cortesServicioMedia;
		
		
		public String getFuente() {
			return fuente;
		}
		public void setFuente(String fuente) {
			this.fuente = fuente;
		}
		public String getEmpresa() {
			return empresa;
		}
		public void setEmpresa(String empresa) {
			this.empresa = empresa;
		}
		public String getTotalUsuariosSinSuministro() {
			return totalUsuariosSinSuministro;
		}
		public void setTotalUsuariosSinSuministro(String totalUsuariosSinSuministro) {
			this.totalUsuariosSinSuministro = totalUsuariosSinSuministro;
		}
		public String getTotalUsuariosConSuministro() {
			return totalUsuariosConSuministro;
		}
		public void setTotalUsuariosConSuministro(String totalUsuariosConSuministro) {
			this.totalUsuariosConSuministro = totalUsuariosConSuministro;
		}
		public String getUltimaActualizacion() {
			return ultimaActualizacion;
		}
		public void setUltimaActualizacion(String ultimaActualizacion) {
			this.ultimaActualizacion = ultimaActualizacion;
		}
		public String getTotalUsuariosAyer() {
			return totalUsuariosAyer;
		}
		public void setTotalUsuariosAyer(String totalUsuariosAyer) {
			this.totalUsuariosAyer = totalUsuariosAyer;
		}
		public List<ItemCortesServicioMedia> getCortesServicioMedia() {
			return cortesServicioMedia;
		}
		public void setCortesServicioMedia(
				List<ItemCortesServicioMedia> cortesServicioMedia) {
			this.cortesServicioMedia = cortesServicioMedia;
		}
	
		
		
		
		
		
}		
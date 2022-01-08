package br.edu.ifrn.crud.controladores;

import java.net.http.HttpHeaders;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.crud.dominio.Arquivo;
import br.edu.ifrn.crud.repository.ArquivoRepository;
/**
 * 
 * @author thyago
 *
 */
@Controller
public class DownloadArquivoController {
	
	/**
	 * Interface ArquivoRepository para busca de arquivos
	 */
	//
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	/**
	 * método que retorna uma entidade de resposta de download do arquivo
	 * 
	 */
	//
	@GetMapping("/download/{idArquivo}")
	public ResponseEntity<?> downloadFile(@PathVariable Long idArquivo, @PathParam("salvar") String salvar){
		
		/**
		 * carregando o arquivo do banco de dados
		 */
		//
		Arquivo arquivoBD = arquivoRepository.findById(idArquivo).get();
		
		/**
		 * caso o parámetro não exista ou for verdadeiro, será efetuado o download
		 */
		//
		String texto = (salvar == null || salvar.equals("true")) ?
				"attachment; filename=\"" + arquivoBD.getNomeArquivo() + "\""
				/**
				 * caso o download não seja efetuado
				 */
				//
				: "inline; filename=\"" + arquivoBD.getNomeArquivo() + "\"";
		
		/**
		 * enviando resposta para a página
		 */
		//
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo()))
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, texto)
				.body(new ByteArrayResource(arquivoBD.getDados()));
		
	}
	
}

package br.com.fr.commons.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class Hands {

	private HandCanvas maoE;
	private HandCanvas maoD;

	private List<Finger> dedos;
	private List<TemplateData> templates;

	public Hands() {
		this(null);
	}

	public Hands(List<TemplateData> templates) {
		this.templates = templates;

		dedos = new ArrayList<>();

		maoE = new HandCanvas(HandCanvas.Direcao.Esquerda);
		maoE.setDedos(createDedosMaoE());

		maoD = new HandCanvas(HandCanvas.Direcao.Direita);
		maoD.setDedos(createDedosMaoD());

	}

	public HandCanvas getMaoE() {
		return maoE;
	}

	public HandCanvas getMaoD() {
		return maoD;
	}

	public List<Finger> getDedos() {
		return dedos;
	}

	private List<Finger> createDedosMaoE() {
		List<Finger> lst = HandsUtil.getInstance().createDedosMaoE();

		if (templates != null) {
			for (TemplateData td : templates) {
				if (td.getId() <= 5) {
					for (Finger d : lst) {
						if (d.getNumDedo() == td.getId()) {

							if (td.getImagem() != null) {
								d.setImagemDigital(td.getImagem());
							}

							if (td.getTemplate() != null) {
								d.setCodigoValidacao(td.getTemplate().getData());
							}

							d.setSituacao(td.getStatus());
							break;
						}
					}
				}
			}
		} else {
			int num = 1;
			for (Finger d : lst) {
				d.setNumDedo(num++);
				d.setImagemDigital(null);
				d.setCodigoValidacao(null);
				d.setSituacao(BiometricsContants.ST_CAPTURA_NAO_CAPTURADO);
			}
		}

		dedos.addAll(lst);

		return lst;
	}

	private List<Finger> createDedosMaoD() {

		List<Finger> lst = HandsUtil.getInstance().createDedosMaoD();

		if (templates != null) {
			for (TemplateData td : templates) {
				if (td.getId() > 5) {
					for (Finger d : lst) {
						if (d.getNumDedo() == td.getId()) {
							if (td.getImagem() != null) {
								d.setImagemDigital(td.getImagem());
							}

							if (td.getTemplate() != null) {
								d.setCodigoValidacao(td.getTemplate().getData());
							}
							d.setSituacao(td.getStatus());
							break;
						}
					}
				}
			}
		} else {
			int num = 6;
			for (Finger d : lst) {
				d.setNumDedo(num++);
				d.setImagemDigital(null);
				d.setCodigoValidacao(null);
				d.setSituacao(BiometricsContants.ST_CAPTURA_NAO_CAPTURADO);
			}
		}

		dedos.addAll(lst);

		return lst;
	}
}

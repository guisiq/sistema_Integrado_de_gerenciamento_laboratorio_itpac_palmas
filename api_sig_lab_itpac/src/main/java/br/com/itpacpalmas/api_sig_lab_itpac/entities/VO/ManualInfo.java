package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

import java.util.ArrayList;
import java.util.List;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;

public class ManualInfo {

    
    private Integer id;
    private String descricao;
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static ManualInfo convert(Manual in) {
        ManualInfo out = new ManualInfo(); 
        out.setDescricao(in.getDescricao());
        out.setId(in.getId());

        return out;
        
    }
    public static List<ManualInfo> convertList(List<Manual> in) {
        List<ManualInfo> out = new ArrayList<ManualInfo>();
        in.forEach(i -> out.add(convert(i))); 
        return out;
        
    }
}


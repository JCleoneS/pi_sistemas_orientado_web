package br.com.projetointegrador.wine.utils;

import java.util.Base64;

public class CriptografiaUtils {
    public static String criptografar(String senhaDescriptografada){
        return Base64.getEncoder().encodeToString(senhaDescriptografada.getBytes());
    }

    public static String descriptografar(String senhaCriptografada){
        return new String (Base64.getDecoder().decode(senhaCriptografada));
    }
}

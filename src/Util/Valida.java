package Util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;

import java.net.URI;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class Valida {


    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }


    public static boolean isEmail(String email) {
        String regex = "^(.+)@(.+)[.](.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    public static void enviaSms(String msg, String dest) {


        // Find your Account Sid and Token at twilio.com/console
        String ACCOUNT_SID = "AC8876dd98617c8baf2b048f6ed4a6aa85";
        String AUTH_TOKEN = "0f3ff9d49a8f0d708fac18cffb6b2ef7";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+55" + dest),
                new com.twilio.type.PhoneNumber("+15312332598"),
                msg)
                .create();


        System.out.println(message.getSid());


    }


    public static boolean isInteiro(String s) {
        // cria um array de char
        char[] c = s.toCharArray();

        String novo = "";

        boolean d = true;
        for (int i = 0; i < c.length; i++) {
            // verifica se o char não é um dígito
            if (!Character.isDigit(c[i])) {

                if(c[i] == '.'){

                }else if(c[i] == ','){
                    c[i] = '.';
                }else  {
                    d = false;
                    break;
                }
            }
            novo = novo + c[i];
        }

        System.out.println("String = " + novo);
        return d;


    }

    public static float getFloat(String s) {
        // cria um array de char
        char[] c = s.toCharArray();

        String novo = "";

        boolean d = true;
        for (int i = 0; i < c.length; i++) {
            // verifica se o char não é um dígito
            if (!Character.isDigit(c[i])) {

                if(c[i] == '.'){

                }else if(c[i] == ','){
                    c[i] = '.';
                }else  {
                    return -1;
                }
            }
            novo = novo + c[i];
        }

        System.out.println("String = " + novo);
        return Float.parseFloat(novo);


    }

}

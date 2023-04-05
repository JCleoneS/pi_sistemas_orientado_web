package br.com.projetointegrador.wine.context.validacao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidarCpfValidator implements ConstraintValidator<ValidarCpf, String> {
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        value = value.replaceAll("[^0-9]", "");
        if (value.length() != 11) {
            return false;
        }
        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(String.valueOf(value.charAt(i)));
            }
            if (digits[9] != calculateDigit(digits, 9) || digits[10] != calculateDigit(digits, 10)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private int calculateDigit(int[] digits, int position) {
        int sum = 0;
        int weight = position + 1;
        for (int i = 0; i < position; i++) {
            sum += digits[i] * weight;
            weight--;
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
}

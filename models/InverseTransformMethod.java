package models;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InverseTransformMethod {

    private final String formula;
    private final List<IExpr> inverseParts = new ArrayList<>();
    private final List<Double> uLimits = new ArrayList<>();
    private final List<String> ranges = new ArrayList<>();
    private final ExprEvaluator util = new ExprEvaluator();

    public InverseTransformMethod(String formula) {
        this.formula = formula;
        buildInversesAndLimits();
    }

    private void buildInversesAndLimits() {
        String cleaned = formula.trim()
            .replaceAll("^\\{\\{", "")
            .replaceAll("\\}\\}$", "");

        String[] pieces = cleaned.split("\\},\\s*\\{");

        double cumulativeArea = 0.0;

        System.out.println("Cleaned: " + cleaned);
        System.out.println("Pieces:");
        for (String p : pieces) {
            System.out.println("[" + p + "]");
        }

        for (String piece : pieces) {
            String[] parts = piece.split(",", 2);
            if(parts.length < 2) {
                throw new IllegalArgumentException("Formato inválido en pieza: " + piece);
            }
            String fx = parts[0].trim();
            String range = parts[1].trim();
            ranges.add(range);

            String[] ab = range.split("&&");
            if (ab.length != 2) {
                throw new IllegalArgumentException("Formato de rango inválido: " + range);
            }

            String a = ab[0].replaceAll("[^0-9.\\-]", "");
            String b = ab[1].replaceAll("[^0-9.\\-]", "");

            if (a.isEmpty() || b.isEmpty()) {
                throw new IllegalArgumentException("Límites de rango no válidos: a='" + a + "', b='" + b + "'");
            }

            String partialIntegral = String.format("Integrate(%s, {x, %s, x})", fx, a);
            IExpr partialCDF = util.evaluate(partialIntegral);

            // CDF completa (sumando área acumulada anterior)
            IExpr fullCDF = util.evaluate(String.format(Locale.US, "(%s) + (%f)", partialCDF, cumulativeArea));

            // Resolver F(x) = u
            IExpr inverse = util.evaluate(String.format("Solve(u == %s, x)", fullCDF.toString()));
            inverseParts.add(inverse);

            // Área total del tramo
            IExpr fullArea = util.evaluate(String.format("Integrate(%s, {x, %s, %s})", fx, a, b));
            double deltaU = fullArea.evalDouble();
            cumulativeArea += deltaU;
            uLimits.add(cumulativeArea);
        }
    }
    
    public double generateValue(double u) {
        if (u <= 0 || u >= 1) throw new IllegalArgumentException("u debe estar en (0,1)");

        int index = 0;
        while (index < uLimits.size() && u > uLimits.get(index)) index++;

        if (index >= inverseParts.size())
            throw new RuntimeException("No se encontró tramo válido para u = " + u);

        String invSolutions = inverseParts.get(index).toString();
        String[] solutions = invSolutions.replaceAll("^\\{\\{|\\}\\}$", "").split("\\},\\s*\\{");

        // Obtener el rango del tramo
        String range = ranges.get(index);
        String[] ab = range.split("&&");
        double a = Double.parseDouble(ab[0].replaceAll("[^0-9.\\-]", ""));
        double b = Double.parseDouble(ab[1].replaceAll("[^0-9.\\-]", ""));

        boolean bIsClosed = ab[1].contains("<=");

        for (String solution : solutions) {
            try {
                String expr = solution.replace("x->", "").trim();
                util.evaluate("f(u_) := " + expr);
                String evalStr = String.format(Locale.US, "N(f(%.15f))", u);
                IExpr result = util.evaluate(evalStr);
                double val = result.evalDouble();

                boolean inRange = bIsClosed ? (val >= a && val <= b) : (val >= a && val < b);

                if (!Double.isNaN(val) && !Double.isInfinite(val) && inRange) {
                    return val;
                }

            } catch (Exception e) {
                System.out.println("Error al evaluar solución: " + e.getMessage());
            }
        }

        throw new RuntimeException("No se encontró solución válida para u = " + u);
    }
    
    public List<String> getInverseFunctions() {
        List<String> output = new ArrayList<>();
        for (IExpr expr : inverseParts) output.add(expr.toString());
        return output;
    }

    public List<Double> getULimits() {
        return uLimits;
    }
}

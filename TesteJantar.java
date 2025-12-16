import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes de unidade para validação da configuração inicial
 * do problema do Jantar dos Filósofos.
 *
 * Foco:
 * - Criação correta de filósofos e garfos
 * - Compartilhamento correto dos recursos (garfos)
 * - Cada filósofo possui exatamente dois garfos
 * - Cada garfo é compartilhado por exatamente dois filósofos
 */
public class JantarTest {

    private static final int NUM_FILOSOFOS = 5;

    private Jantar jantar;

    @BeforeEach
    void setup() {
        jantar = new Jantar(NUM_FILOSOFOS);
        jantar.iniciar();
    }

    @Test
    void deveCriarQuantidadeCorretaDeFilosofosEGarfos() {
        List<Fisolofo> filosofos = jantar.getFilosofos();
        List<Garfo> garfos = jantar.getGarfos();

        assertNotNull(filosofos, "Lista de filósofos não deve ser nula");
        assertNotNull(garfos, "Lista de garfos não deve ser nula");

        assertEquals(NUM_FILOSOFOS, filosofos.size(),
                "Quantidade incorreta de filósofos");

        assertEquals(NUM_FILOSOFOS, garfos.size(),
                "Quantidade incorreta de garfos");
    }

    @Test
    void cadaFilosofoDevePossuirDoisGarfos() {
        for (Fisolofo f : jantar.getFilosofos()) {
            assertNotNull(f.getGarfoEsquerdo(),
                    "Garfo esquerdo não deve ser nulo");

            assertNotNull(f.getGarfoDireito(),
                    "Garfo direito não deve ser nulo");

            assertNotSame(
                    f.getGarfoEsquerdo(),
                    f.getGarfoDireito(),
                    "Um filósofo não pode usar o mesmo garfo duas vezes"
            );
        }
    }

    @Test
    void cadaGarfoDeveSerCompartilhadoPorDoisFilosofos() {
        List<Fisolofo> filosofos = jantar.getFilosofos();

        for (Garfo garfo : jantar.getGarfos()) {
            int contador = 0;

            for (Fisolofo f : filosofos) {
                if (f.getGarfoEsquerdo() == garfo ||
                    f.getGarfoDireito() == garfo) {
                    contador++;
                }
            }

            assertEquals(2, contador,
                    "Cada garfo deve ser compartilhado por exatamente dois filósofos");
        }
    }

    @Test
    void todosOsGarfosDevemEstarConectadosAoSistema() {
        Set<Garfo> garfosReferenciados = new HashSet<>();

        for (Fisolofo f : jantar.getFilosofos()) {
            garfosReferenciados.add(f.getGarfoEsquerdo());
            garfosReferenciados.add(f.getGarfoDireito());
        }

        assertEquals(
                jantar.getGarfos().size(),
                garfosReferenciados.size(),
                "Existem garfos não utilizados ou referências duplicadas incorretas"
        );
    }
}

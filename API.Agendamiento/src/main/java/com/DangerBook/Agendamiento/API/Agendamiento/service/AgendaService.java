@Service
@Transactional
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findById(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
    }

    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
}

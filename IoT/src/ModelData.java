public class ModelData
{
    private final static ModelData instance = new ModelData();
    private Model model;
    private ModelData(){}
    public static ModelData getInstance(){
        return instance;
    }
    public void setModel(Model model){ this.model = model; }
    public Model getModel(){ return model; }

}
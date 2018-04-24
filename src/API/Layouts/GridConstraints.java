package API.Layouts;

public class GridConstraints {
    public boolean isResizing;
    public float resizeScale;

    private GridConstraints(float resizeScale, boolean isResizing){
        this.isResizing = isResizing;
        this.resizeScale = resizeScale;
    }

    public GridConstraints(float resizeScale){
        this(resizeScale, true);
    }

    public GridConstraints(boolean isResizing){
        this(1.f, isResizing);
    }

    public GridConstraints(){
        this(1.f, true);
    }
}

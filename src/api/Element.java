package api;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JComponent;

import api.components.Sprite;
import api.utility.Vector;

/**
 * Classe principale che identifica la base di qualunque oggetto interagibile inseribile in una {@link AreaMap}.
 * <p>Oggetto posizionabile con un'immagine {@link Sprite}, interagibile tramite mouse, e posizionabile.
 * <p>&Egrave; inoltre possibile attaccare ad un {@code Element} qualunque {@code JComponent} sincronizzando cos&igrave; la posizione.
 *
 * @see AreaMap
 * @see Sprite
 * @see Vector
 * @author Simone Russo
 */
public class Element implements MouseListener {

    /**
     * La posizione nell'{@link AreaMap} in cui è stato aggiunto l'{@code Element}
     */
    private Vector location;

    /**
     * Immagine posizionabile e ruotabile dell'{@code Element}
     */
    private Sprite sprite;

    /**
     * Lista contenente tutti i {@code JComponent} relativi attaccati
     */
    private HashMap<JComponent, Vector> attachedComps;

    /**
     * l'{@link AreaMap} in cui è stato inserito l'{@code Element}
     */
    private AreaMap areaMap;

    /**
     * Costruisce un {@code Element} basandosi sull'imagine da associare alla {@link Sprite} e la scala da applicare ad essa.
     * @param filename nome di un'immagine nella cartella {@code assets/textures/} da associare alla {@link Sprite}
     * @param scale la scala da applicare alla {@link Sprite} associata all'{@code Element}
     */
	public Element(String filename, double scale) {
		attachedComps = new HashMap<>();
		sprite = new Sprite(filename, scale);
		location = new Vector();
	}

    /**
     * Costruisce un {@code Element} basandosi sull'imagine da associare alla {@link Sprite} e non applicando nessuna scala.
     * @param filename nome di un'immagine nella cartella {@code assets/textures/} da associare alla {@link Sprite}
     */
	public Element(String filename) {
		this(filename, 1);
	}

    /**
     * Costruisce un {@code Element} di default con una {@link Sprite} nulla.
     */
	public Element() {
		this(null);
	}

    /**
     * Imposta la position dell'{@code Element}
     * @param location posizione nell'{@link AreaMap}.
     */
    public void setLocation(Vector location){
    	setLocation(location.x, location.y);
    }


    private void updateSyncedComponentsLocation(int x, int y) {
    	attachedComps.forEach((comp, loc) -> {
    		comp.setLocation(loc.toPoint());
    		comp.setLocation(location.x - comp.getWidth()/2 + comp.getX(), location.y - comp.getHeight()/2 + comp.getY());
    	});
    }
    
    public void setLocation(int x, int y){
    	updateSyncedComponentsLocation(x, y);
    	
    	this.location.x = x;
    	this.location.y = y;
        
        if(sprite != null)
        	sprite.setLocation(location.x - sprite.getWidth()/2, location.y - sprite.getHeight()/2);
    }

    public Vector getLocation(){
        return this.location;
    }

    public void setRotation(float degrees){
    	if(sprite != null)
    		sprite.rotate(degrees);
    }

    public float getRotation(){
    	if(sprite != null)
    		return sprite.getRotation();
    	
    	return 0;
    }

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(String filename, double scale) {
		this.sprite = new Sprite(filename, scale);
		sprite.addMouseListener(this);
	}
	
	public void setSprite(String filename) {
		this.sprite = new Sprite(filename);
		sprite.addMouseListener(this);
	}

	public AreaMap getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(AreaMap areaMap) {
		this.areaMap = areaMap;
	}
    
	public void addRelativeComponent(JComponent component, Vector relative, int zindex) {
		attachRelativeComponent(component, relative);
		areaMap.addComponent(component, location.add(relative), zindex);
	}
	
	public void addRelativeComponent(JComponent component, Vector relative) {
		addRelativeComponent(component, relative, 10);
	}
	
	public void addRelativeComponent(JComponent component, int zindex) {
		addRelativeComponent(component, new Vector(), zindex);
	}
	
	public void addRelativeComponent(JComponent component) {
		addRelativeComponent(component, new Vector(), 10);
	}
	
	public void attachRelativeComponent(JComponent component, Vector relative) {
		relative.y *= -1;
		attachedComps.put(component, relative);
	}
	
	public void attachRelativeComponent(JComponent component) {
		attachRelativeComponent(component, new Vector());
	}
	
	public void detachRelativeComponent(JComponent component) {
		attachedComps.remove(component);
	}

	public JComponent[] getAttachedComps() {
		JComponent[] tempAttachedComps = new JComponent[attachedComps.size()];
		return attachedComps.keySet().toArray(tempAttachedComps);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}

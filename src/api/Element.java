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
 * <p>&Egrave; inoltre possibile attaccare ad un {@code Element} qualunque {@code JComponent} sincronizzandone cos&igrave; la posizione.
 *
 * @see AreaMap
 * @see Sprite
 * @see Vector
 * @see JComponent
 * @author Simone Russo
 */
public abstract class Element implements MouseListener {

    /**
     * La posizione nell'{@link AreaMap} in cui &egrave; stato aggiunto l'{@code Element}.
     */
    private Vector location;

    /**
     * Immagine posizionabile e ruotabile.
     */
    private Sprite sprite;

    /**
     * Lista contenente tutti i {@code JComponent} relativi attaccati.
     */
    private HashMap<JComponent, Vector> attachedComps;

    /**
     * l'{@link AreaMap} in cui &egrave; stato inserito l'{@code Element}.
     */
    private AreaMap areaMap;

    /**
     * Costruisce un {@code Element} basandosi sull'imagine da associare alla {@link Sprite} e la scala da applicare ad essa.
     * @param filename nome di un'immagine nella cartella {@code assets/textures/} da associare alla {@link Sprite}
     * @param scale la scala da applicare alla {@link Sprite} di questo {@code Element}
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
     * Costruisce un {@code Element} di default con {@link Sprite} nulla.
     */
	public Element() {
		this(null);
	}

	/**
	 * Aggiorna la posizione di tutti i {@code JComponent} attaccati all'{@code Element}.
	 * @param x coordinata orizzontale assoluta dell'{@code Element} parente
	 * @param y coordinata verticale assoluta dell'{@code Element} parente
	 */
	private void updateSyncedComponentsLocation(int x, int y) {
		attachedComps.forEach((comp, loc) -> {
			comp.setLocation(loc.toPoint());
			comp.setLocation(location.x - comp.getWidth()/2 + comp.getX(), location.y - comp.getHeight()/2 + comp.getY());
		});
	}

    /**
     * Imposta la {@link #location} di questo {@code Element} basandosi su un {@code Vector}.
     * @param location posizione nell'{@link AreaMap}.
     */
    public void setLocation(Vector location){
    	setLocation(location.x, location.y);
    }

	/**
	 * Imposta la {@link #location} di questo {@code Element} basandosi su due cordinate.
	 * @param x coordinata orizzontale assoluta da impostare all'{@code Element}
	 * @param y coordinata verticale assoluta da impostare all'{@code Element}
	 */
	public void setLocation(int x, int y){
    	updateSyncedComponentsLocation(x, y);
    	
    	this.location.x = x;
    	this.location.y = y;
        
        if(sprite != null)
        	sprite.setLocation(location.x - sprite.getWidth()/2, location.y - sprite.getHeight()/2);
    }

	/**
	 * Ritorna la attuale {@link #location} di questo {@code Element}.
	 */
	public Vector getLocation(){
        return this.location;
    }

	/**
	 * Imposta la rotazione della {@link Sprite} di questo {@code Element} basandosi su gradi di rotazione.
	 * @param degrees gradi di rotazione.
	 */
	public void setRotation(float degrees){
    	if(sprite != null)
    		sprite.rotate(degrees);
    }

	/**
	 * Ritorna i gradi di rotazione della {@link Sprite} di questo {@code Element}
	 * @return gradi di rotazione, se non &egrave; presente una {@link Sprite} ritorna 0.
	 */
	public float getRotation(){
    	if(sprite != null)
    		return sprite.getRotation();
    	
    	return 0;
    }

	/**
	 * Ritorna la {@link #sprite} di questo {@code Element}.
	 * @return la {@code Sprite} di questo {@code Element}, se non viene impostata ne ritorna una con l'immagine null.png.
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Imposta la {@link #sprite} di questo {@code Element} basandosi sul nome del file dell'immagine e la scala.
	 * @param filename nome del file dell'immagine presente nella cartella {@code assets/textures}.
	 * @param scale scala da applicare all'immagine.
	 */
	public void setSprite(String filename, double scale) {
		this.sprite = new Sprite(filename, scale);
		sprite.addMouseListener(this);
	}

	/**
	 * Imposta la {@link #sprite} di questo {@code Element} basandosi sul nome del file dell'immagine e mantenendo le dimensioni dell'immagine.
	 * @param filename nome del file dell'immagine presente nella cartella {@code assets/textures}.
	 */
	public void setSprite(String filename) {
		this.sprite = new Sprite(filename);
		sprite.addMouseListener(this);
	}

	/**
	 * Ritorna la {@link #areaMap} di questo {@code Element}.
	 */
	public AreaMap getAreaMap() {
		return areaMap;
	}

	/**
	 * Imposta l'{@link #areaMap} di questo {@code Element} basandosi su un'{@code AreaMap}.
	 * <p>Questo metodo viene utilizzato esclusivamente dalla mappa quando l'{@code Element}
	 * viene aggiunto ad essa nel metodo {@link AreaMap#addElement(Element, Vector, int)}, per
	 * inserire il riferimento di se stessa.</p>
	 * @param areaMap l'{@code AreaMap} da associare.
	 */
	void setAreaMap(AreaMap areaMap) {
		this.areaMap = areaMap;
	}

	/**
	 * Aggiunge un {@code JComponent} all'{@link AreaMap} in cui &egrave; presente questo {@code Element}
	 * sincronizzandone la posizione.
	 * @param component il {@code JComponent} da sincronizzare.
	 * @param relative la posizione relativa rispetto all'oggetto.
	 * @param zindex il livello di profondità che deve avere una volta aggiunto nella mappa.
	 */
	public void addRelativeComponent(JComponent component, Vector relative, int zindex) {
		attachRelativeComponent(component, relative);
		areaMap.addComponent(component, location.add(relative), zindex);
	}

	/**
	 * Aggiunge un {@code JComponent} all'{@link AreaMap} in cui &egrave; presente questo {@code Element}
	 * sincronizzandone la posizione e impostando come zindex 10.
	 * @param component il {@code JComponent} da sincronizzare.
	 * @param relative la posizione relativa rispetto all'oggetto.
	 */
	public void addRelativeComponent(JComponent component, Vector relative) {
		addRelativeComponent(component, relative, 10);
	}

	/**
	 * Aggiunge un {@code JComponent} all'{@link AreaMap} in cui &egrave; presente questo {@code Element}
	 * sincronizzandone la posizione al centro.
	 * @param component il {@code JComponent} da sincronizzare.
	 * @param zindex il livello di profondità che deve avere una volta aggiunto nella mappa.
	 */
	public void addRelativeComponent(JComponent component, int zindex) {
		addRelativeComponent(component, new Vector(), zindex);
	}

	/**
	 * Aggiunge un {@code JComponent} all'{@link AreaMap} in cui &egrave; presente questo {@code Element}
	 * sincronizzandone la posizione la posizione al centro e impostando come zindex 10.
	 * @param component il {@code JComponent} da sincronizzare.
	 */
	public void addRelativeComponent(JComponent component) {
		addRelativeComponent(component, new Vector(), 10);
	}

	/**
	 * Sincronizza la posizione di un {@code JComponent} gi&agrave; aggiunto alla mappa in cui &egrave; presente questo
	 * {@code Element} con se stesso.
	 * @param component il {@code JComponent} da sincronizzare.
	 * @param relative la posizione relativa rispetto all'oggetto.
	 */
	public void attachRelativeComponent(JComponent component, Vector relative) {
		relative.y *= -1;
		attachedComps.put(component, relative);
	}

	/**
	 * Sincronizza la posizione di un {@code JComponent} gi&agrave; aggiunto alla mappa in cui &egrave; presente questo
	 * {@code Element} con il centro di se stesso.
	 * @param component il {@code JComponent} da sincronizzare.
	 */
	public void attachRelativeComponent(JComponent component) {
		attachRelativeComponent(component, new Vector());
	}

	/**
	 * Rimuove il {@code JComponent} scelto fra quelli sincronizzati alla posizione di questo {@code Element}.
	 * @param component il {@code JComponent} da scollegare.
	 */
	public void detachRelativeComponent(JComponent component) {
		attachedComps.remove(component);
	}

	/**
	 * Ritorna un {@code array} dei {@code JComponent} attaccati a questo {@code Element}
	 */
	JComponent[] getAttachedComps() {
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

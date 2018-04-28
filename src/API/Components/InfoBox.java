package API.Components;

import javax.swing.JPanel;

public class InfoBox extends JPanel implements ActorComponent {
	private ActorComponent parent;
	
	@Override
	public ActorComponent getAttached() {
		return parent;
	}

	@Override
	public void attachTo(ActorComponent parent) {
		this.parent = parent;
	}

}

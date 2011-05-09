package de.leuphana.quadt.drone.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.leuphana.quadt.drone.DroneControl;

public class DroneControlPanel extends JPanel {

	private de.leuphana.quadt.drone.DroneControl droneControl;
	private JSlider baseSpeedJSlider;
	private JSlider horzSpeedJSlider;
	private JSlider rotationSpeedJSlider;
	private JSlider tiltSpeedJSlider;
	private JSlider verticalSpeedJSlider;

	public DroneControlPanel(DroneControl droneControl) {
		this();
		this.droneControl = droneControl;
		initBindings();
	}

	public DroneControlPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0E-4 };
		setLayout(gridBagLayout);

		JLabel baseSpeedLabel = new JLabel("BaseSpeed:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 0;
		add(baseSpeedLabel, labelGbc_0);

		baseSpeedJSlider = new JSlider();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 5);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 0;
		add(baseSpeedJSlider, componentGbc_0);

		JLabel horzSpeedLabel = new JLabel("HorzSpeed:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		add(horzSpeedLabel, labelGbc_1);

		horzSpeedJSlider = new JSlider();
		horzSpeedJSlider.setValue(5);
		horzSpeedJSlider.setMaximum(10);
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		add(horzSpeedJSlider, componentGbc_1);

		JLabel rotationSpeedLabel = new JLabel("RotationSpeed:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 2;
		add(rotationSpeedLabel, labelGbc_2);

		rotationSpeedJSlider = new JSlider();
		rotationSpeedJSlider.setValue(5);
		rotationSpeedJSlider.setMaximum(10);
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.insets = new Insets(5, 0, 5, 5);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 2;
		add(rotationSpeedJSlider, componentGbc_2);

		JLabel tiltSpeedLabel = new JLabel("TiltSpeed:");
		GridBagConstraints labelGbc_3 = new GridBagConstraints();
		labelGbc_3.insets = new Insets(5, 5, 5, 5);
		labelGbc_3.gridx = 0;
		labelGbc_3.gridy = 3;
		add(tiltSpeedLabel, labelGbc_3);

		tiltSpeedJSlider = new JSlider();
		tiltSpeedJSlider.setMaximum(10);
		tiltSpeedJSlider.setValue(5);
		GridBagConstraints componentGbc_3 = new GridBagConstraints();
		componentGbc_3.insets = new Insets(5, 0, 5, 5);
		componentGbc_3.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_3.gridx = 1;
		componentGbc_3.gridy = 3;
		add(tiltSpeedJSlider, componentGbc_3);

		JLabel verticalSpeedLabel = new JLabel("VerticalSpeed:");
		GridBagConstraints labelGbc_4 = new GridBagConstraints();
		labelGbc_4.insets = new Insets(5, 5, 5, 5);
		labelGbc_4.gridx = 0;
		labelGbc_4.gridy = 4;
		add(verticalSpeedLabel, labelGbc_4);

		verticalSpeedJSlider = new JSlider();
		verticalSpeedJSlider.setValue(5);
		verticalSpeedJSlider.setMaximum(10);
		GridBagConstraints componentGbc_4 = new GridBagConstraints();
		componentGbc_4.insets = new Insets(5, 0, 5, 5);
		componentGbc_4.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_4.gridx = 1;
		componentGbc_4.gridy = 4;
		add(verticalSpeedJSlider, componentGbc_4);

		// verticalSpeedJSlider.add
		// if (droneControl != null) {
		// m_bindingGroup = initDataBindings();
		// }
	}

	private void initBindings() {
		addBinding(baseSpeedJSlider, "baseSpeed");
		addBinding(horzSpeedJSlider, "horzSpeed");
		addBinding(rotationSpeedJSlider, "rotationSpeed");
		addBinding(tiltSpeedJSlider, "tiltSpeed");
		addBinding(verticalSpeedJSlider, "verticalSpeed");
	}

	private void addBinding(final JSlider slider, final String propertyName) {
		if (propertyName != null && propertyName.length() > 1)
			slider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					int value = slider.getValue();
					System.out.println("value changed");
					callSetter(droneControl, propertyName, value);
					// droneControl..setRotationSpeed(value);
					// System.out.println("rotationSpeed: " + value);
				}
			});

		droneControl.addPropertyChangeListener(propertyName,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						if (arg0.getNewValue() instanceof Integer) {
							slider.setValue((Integer) arg0.getNewValue());
							System.out.println("rotationSpeed changed: "
									+ arg0.getNewValue());
						}
					}
				});
	}

	private void callSetter(Object obj, final String propertyName, int value) {
		char upperCaseFirstChar = Character.toUpperCase(propertyName.charAt(0));

		String setterName = "set" + upperCaseFirstChar
				+ propertyName.substring(1);
		try {
			Method declaredMethod = obj.getClass().getDeclaredMethod(setterName,
					int.class);
			declaredMethod.invoke(obj, value);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// protected BindingGroup initDataBindings() {
	// BeanProperty<de.leuphana.quadt.drone.DroneControl, java.lang.Integer>
	// baseSpeedProperty = BeanProperty
	// .create("baseSpeed");
	// BeanProperty<javax.swing.JSlider, java.lang.Integer> valueProperty =
	// BeanProperty
	// .create("value");
	// AutoBinding<de.leuphana.quadt.drone.DroneControl, java.lang.Integer,
	// javax.swing.JSlider, java.lang.Integer> autoBinding = Bindings
	// .createAutoBinding(AutoBinding.UpdateStrategy.READ,
	// droneControl, baseSpeedProperty, baseSpeedJSlider,
	// valueProperty);
	// autoBinding.bind();
	// //
	// BeanProperty<de.leuphana.quadt.drone.DroneControl, java.lang.Integer>
	// horzSpeedProperty = BeanProperty
	// .create("horzSpeed");
	// BeanProperty<javax.swing.JSlider, java.lang.Integer> valueProperty_1 =
	// BeanProperty
	// .create("value");
	// AutoBinding<de.leuphana.quadt.drone.DroneControl, java.lang.Integer,
	// javax.swing.JSlider, java.lang.Integer> autoBinding_1 = Bindings
	// .createAutoBinding(AutoBinding.UpdateStrategy.READ,
	// droneControl, horzSpeedProperty, horzSpeedJSlider,
	// valueProperty_1);
	// autoBinding_1.bind();
	// //
	// BeanProperty<de.leuphana.quadt.drone.DroneControl, java.lang.Integer>
	// rotationSpeedProperty = BeanProperty
	// .create("rotationSpeed");
	// BeanProperty<javax.swing.JSlider, java.lang.Integer> valueProperty_2 =
	// BeanProperty
	// .create("value");
	// AutoBinding<de.leuphana.quadt.drone.DroneControl, java.lang.Integer,
	// javax.swing.JSlider, java.lang.Integer> autoBinding_2 = Bindings
	// .createAutoBinding(AutoBinding.UpdateStrategy.READ,
	// droneControl, rotationSpeedProperty,
	// rotationSpeedJSlider, valueProperty_2);
	// autoBinding_2.bind();
	// //
	// BeanProperty<de.leuphana.quadt.drone.DroneControl, java.lang.Integer>
	// tiltSpeedProperty = BeanProperty
	// .create("tiltSpeed");
	// BeanProperty<javax.swing.JSlider, java.lang.Integer> valueProperty_3 =
	// BeanProperty
	// .create("value");
	// AutoBinding<de.leuphana.quadt.drone.DroneControl, java.lang.Integer,
	// javax.swing.JSlider, java.lang.Integer> autoBinding_3 = Bindings
	// .createAutoBinding(AutoBinding.UpdateStrategy.READ,
	// droneControl, tiltSpeedProperty, tiltSpeedJSlider,
	// valueProperty_3);
	// autoBinding_3.bind();
	// //
	// BeanProperty<de.leuphana.quadt.drone.DroneControl, java.lang.Integer>
	// verticalSpeedProperty = BeanProperty
	// .create("verticalSpeed");
	// BeanProperty<javax.swing.JSlider, java.lang.Integer> valueProperty_4 =
	// BeanProperty
	// .create("value");
	// AutoBinding<de.leuphana.quadt.drone.DroneControl, java.lang.Integer,
	// javax.swing.JSlider, java.lang.Integer> autoBinding_4 = Bindings
	// .createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
	// droneControl, verticalSpeedProperty,
	// verticalSpeedJSlider, valueProperty_4);
	// autoBinding_4.bind();
	// //
	// BindingGroup bindingGroup = new BindingGroup();
	// bindingGroup.addBinding(autoBinding);
	// bindingGroup.addBinding(autoBinding_1);
	// bindingGroup.addBinding(autoBinding_2);
	// bindingGroup.addBinding(autoBinding_3);
	// bindingGroup.addBinding(autoBinding_4);
	// //
	// return bindingGroup;
	// }

	public DroneControl getDroneControl() {
		return droneControl;
	}

}

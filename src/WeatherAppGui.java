import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class WeatherAppGui extends JFrame {

    private JSONObject weatherData;
    private JLabel  weatherConditionsImage, tempText, weatherConditionDesc, humidityText, windSpeedText;
    private JTextField searchTextField;


    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        guiComponents();
    }

    private void guiComponents() {
        searchTextField();

        weatherConditionsImage();

        tempText();

        weatherConditionDesc();

        humidityImage();

        humidityText();

        windSpeedImage();

        windSpeedText();

        searchButton();
    }

    public void searchButton() {
        JButton searchButton = new JButton(loadImage("search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition) {
                    case "Clear":
                        weatherConditionsImage.setIcon(loadImage("clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionsImage.setIcon(loadImage("cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionsImage.setIcon(loadImage("rain.png"));
                        break;
                    case "Snow":
                        weatherConditionsImage.setIcon(loadImage("snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                tempText.setText(temperature + " C");

                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity: </b>" + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Wind Speed: </b>" + windspeed + "km/h</html>");

            }
        });
        add(searchButton);
    }

    public void searchTextField() {
        searchTextField = new JTextField();
        searchTextField.setBounds(15,15, 351,45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);
    }

    private void weatherConditionsImage() {
        weatherConditionsImage = new JLabel(loadImage("clear.png"));
        weatherConditionsImage.setBounds(0, 105, 450, 217);
        add(weatherConditionsImage);
    }

    private void tempText() {
        tempText = new JLabel("55°F");
        tempText.setFont(new Font("Dialog", Font.BOLD, 48));
        tempText.setHorizontalAlignment(SwingConstants.CENTER);
        tempText.setBounds(0, 350, 450, 54);
        add(tempText);
    }

    private void weatherConditionDesc() {
        weatherConditionDesc = new JLabel("Clear");
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        weatherConditionDesc.setBounds(0, 405, 450,36);
        add(weatherConditionDesc);
    }

    private void humidityImage() {
        JLabel humidityImage = new JLabel(loadImage("humidity.png"));
        humidityImage.setBounds(15, 500,74,66);
        add(humidityImage);
    }

    private void humidityText() {
        humidityText = new JLabel("<html><b>Humidity:</b> 100%</html>");
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        humidityText.setBounds(90, 500, 85, 55);
        add(humidityText);
    }

    private void windSpeedImage() {
        JLabel windSpeedImage = new JLabel(loadImage("windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);
        add(windSpeedImage);
    }

    private void windSpeedText() {
        windSpeedText = new JLabel("<html><b>Wind Speed:</b> 10mph</html>");
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        windSpeedText.setBounds(310, 500, 100, 66);
        add(windSpeedText);
    }

    private ImageIcon loadImage(String resourcePath) {

        InputStream is = WeatherAppGui.class.getResourceAsStream(resourcePath);

        try {
            BufferedImage image = ImageIO.read(is);
            return  new ImageIcon((image));
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("could not find resource");
        return null;
    }
}

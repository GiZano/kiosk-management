# kiosk-management
Java program to manage a smoothies kiosk with its own deposit and menu (Code in italian)

## What can you do?

You can manage your own kiosk:
<ul>
  <li>You can create from 0 your own deposit by adding new fruits and their state</li>
  <li>You can create your own menu of smoothies from 0 and decide their recipe based on the fruits you own in the deposit</li>
  <li>Simulate the preparation of the smoothies in you menu with all the associated possible errors</li>
</ul>

## Main classes

### Kiosk

<ul>
  <li>Handles an Arraylist for the fruit deposit and one for the Smoothie menu</li>
  <li>Has methos to get those lists and to manage them, for example printing or finding items</li>
</ul>

### Fruit

<ul>
  <li>Has attributes to show its actual state:
    <ul>
      <li>Name</li>
      <li>Avaiable quantity</li>
      <li>Maturation</li>
    </ul>
  </li>
  <li>Has methods to access the attributes and to manage the avaiable quantity</li>
</ul>

### Smoothie

<ul>
  <li>Has attributes to show what it needs:
  <ul>
    <li>Ideal temperature of service</li>
    <li>An Hashmap with the list of the fruits needed to make it and the quantity</li>
  </ul>
  </li>
  <li>Methos to access the attributes</li>
</ul>


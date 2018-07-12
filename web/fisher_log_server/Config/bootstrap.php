<?php
Configure::write('Security.google-clientid', '1073853792766-lfm8inkb043fpu0vgjg28ie87eicarkn.apps.googleusercontent.com');
Configure::write('Security.google-clientsecret', 'boE8ymXBuxqEJDX325ZLTWi5');
Configure::write('Security.from', 'desarrollogis@gmail.com');
Configure::write('Security.autoregister', array('user' => TRUE));
CakePlugin::load('Security', array('bootstrap' => true));
?>

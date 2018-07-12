<div>
<?php echo $this->element('Main.species_header'); ?>
<div class="row">
<div class="col-md-12">
<?php
echo $this->Form->create('Species');
echo $this->Form->input('name', array('class' => 'form-control'));
echo $this->Form->input('stock', array('default' => '0'));
echo $this->Form->submit(__d('main', 'Add'), array('class' => 'form-control'));
echo $this->Form->end();
?>
</div>
</div>
</div>

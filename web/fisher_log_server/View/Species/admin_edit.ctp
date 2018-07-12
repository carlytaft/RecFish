<div>
<?php echo $this->element('Main.species_header'); ?>
<div class="row">
<div class="col-md-6">
<div class="thumbnail">
<img src="<?php echo $this->request->data['Species']['image'] ?: 'http://icons.iconarchive.com/icons/icons8/windows-8/64/Animals-Fish-2-icon.png'; ?>"/>
</div>
</div>
<div class="col-md-6">
<?php
echo $this->Form->create('Species');
echo $this->Form->hidden('id');
echo $this->Form->input('name', array('class' => 'form-control'));
echo $this->Form->input('image', array('class' => 'form-control'));
echo $this->Form->input('stock');
echo $this->Form->submit(__d('main', 'Update'), array('class' => 'form-control'));
echo $this->Form->end();
?>
</div>
</div>
</div>

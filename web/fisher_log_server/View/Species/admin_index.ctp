<?php
$this->start('css');
echo $this->Html->css('Main.species/admin_index');
$this->end();
$defaultImage = 'http://icons.iconarchive.com/icons/icons8/windows-8/64/Animals-Fish-2-icon.png';
?>
<div>
<?php echo $this->element('Main.species_header'); ?>
<?php foreach ($species as $element): ?>
<?php $link = Router::url('/admin/main/species/edit/' . $element['Species']['id'], TRUE); ?>
<div class="well well-lg" onclick="window.location='<?php echo $link; ?>';">
<div class="row">
<div class="col-md-3">
<div class="thumbnail">
<img src="<?php echo $element['Species']['image'] ?: $defaultImage; ?>"/>
</div>
</div>
<div class="col-md-9">
<h2>
<a href="<?php echo $link; ?>">
<?php if ($element['Species']['stock']): ?>
<i class="fa fa-gear">

</i>
<?php else: ?>
<i class="fa fa-user">

</i>
<?php endif; ?>
<?php echo $element['Species']['name']; ?>
</a>
</h2>
<img src="<?php echo $this->Gravatar->get($element['User']['email'], 64); ?>" alt="<?php echo $element['User']['username']; ?>" class="img-thumbnail"/>
</div>
<div class="clearfix">

</div>
</div>
</div>
<?php endforeach; ?>
</div>

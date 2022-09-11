<div class="row">
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Patient Name</td>
				<td width="2%">:</td>
				<td width="70%">${patient.patientName}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">UMR No</td>
				<td width="2%">:</td>
				<td width="70%">${patient.umrNumber}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">IP number</td>
				<td width="2%">:</td>
				<td width="70%">${patient.ipNumber}</td>
			</tr>	
		</table>
	</div>																
</div>
<div class="row">
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Admission Date</td>
				<td width="2%">:</td>
				<td width="70%">${not empty patient.admittedDate ? patient.admittedDate.format(localDateTimeFormatter) : ''}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Doctor Name</td>
				<td width="2%">:</td>
				<td width="70%">${patient.doctor}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
	</div>								
</div>	
<div class="row">
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Bed Cd</td>
				<td width="2%">:</td>
				<td width="70%">${patient.bed.bedCode}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Floor / Ward Name</td>
				<td width="2%">:</td>
				<td width="70%">${patient.bed.floor.floorName} / ${patient.bed.wardName}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">NBM</td>
				<td width="2%">:</td>
				<td width="70%">${patient.nbm ? 'Yes' : 'No'}</td>
			</tr>	
		</table>
	</div>																
</div>	
<div class="row" ${patient.nbm ? 'style="display: none;"' : ''}>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Diet Type- Oral Solid</td>
				<td width="2%">:</td>
				<td width="70%">${patient.dietTypeOralSolid.value}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Extra Liquid</td>
				<td width="2%">:</td>
				<td width="70%">${patient.extraLiquid ? 'Yes' : 'No'}</td>
			</tr>	
		</table>
	</div>								
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Diet Type- Oral Liquid/TF</td>
				<td width="2%">:</td>
				<td width="70%">${patient.dietTypeOralLiquidTF.value}</td>
			</tr>	
		</table>
	</div>															
</div>		
<div class="row" ${patient.nbm or empty patient.dietTypeOralLiquidTF? 'style="display: none;"' : ''}>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Diet Sub Type</td>
				<td width="2%">:</td>
				<td width="70%">${patient.dietSubType.value}</td>
			</tr>	
		</table>
	</div>	
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Quantity</td>
				<td width="2%">:</td>
				<td width="70%">${patient.quantity.valueStr}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Frequency</td>
				<td width="2%">:</td>
				<td width="70%">${patient.frequency.valueStr}</td>
			</tr>	
		</table>
	</div>																
</div>							
<div class="row">
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Medical Co-morbidities</td>
				<td width="2%">:</td>
				<td width="70%">${patient.medicalComorbiditiesString}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Diagonosis</td>
				<td width="2%">:</td>
				<td width="70%">${patient.diagonosisString}</td>
			</tr>	
		</table>
	</div>
	<div class="col-sm-4">
		<table class="table-sm">
			<tr>
				<td width="28%">Special Notes By Nursing</td>
				<td width="2%">:</td>
				<td width="70%">${patient.specialNotesByNursingString}</td>
			</tr>	
		</table>
	</div>																
</div>
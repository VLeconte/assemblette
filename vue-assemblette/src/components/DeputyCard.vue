<script setup lang="ts">

defineProps({
  deputy: {
    type: Object,
    required: true
  }
});

const getPoliticalColor = function (politicalGroup: string) {
  switch (politicalGroup) {
    case 'Rassemblement National':
      return "text-blue-900"
    case 'Ensemble pour la République':
      return "text-yellow-300"
    case 'La France insoumise - Nouveau Front Populaire':
      return "text-red-700"
    case 'Socialistes et apparentés':
      return "text-red-300"
    case 'Droite Républicaine':
      return "text-sky-500"
    case 'Écologiste et Social':
      return "text-lime-500"
    case 'Les Démocrates':
      return "text-amber-500"
    case 'Horizons & Indépendants':
      return "text-blue-700"
    case 'Libertés, Indépendants, Outre-mer et Territoires':
      return "text-pink-300"
    case 'Gauche Démocrate et Républicaine':
      return "text-red-500"
    case 'UDR':
      return "text-blue-950"
    case 'Non-inscrit':
      return "text-stone-400"
    default:
      return "text-gray-500"
  }
}
</script>

<template>
  <div class="flex flex-col gap-y-1 gap-x-2 rounded-xl bg-white m-6 p-3 shadow-lg outline outline-black/5">
    <div class="text-lg text-gray-700">
      <p>{{ deputy.prenom }} {{ deputy.nom }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-top">
      <i :class="['text-lg', 'pi', 'pi-building-columns', getPoliticalColor(deputy.groupePolitiqueComplet)]"></i>
      <p class="text-sm text-gray-500">{{ deputy.groupePolitiqueComplet }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-top">
      <i class="text-lg pi pi-map-marker text-orange-300"></i>
      <p class="text-sm text-gray-500">{{ deputy.region }}, {{ deputy.departement }}</p>
    </div>
  </div>
</template>

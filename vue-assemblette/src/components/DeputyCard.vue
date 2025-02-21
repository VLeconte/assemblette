<script setup lang="ts">
import { type Deputy } from '@/entities/deputy';
import type { Mandate } from '@/entities/mandate';
import { type PropType } from 'vue';
import _ from 'lodash'

defineProps({
  deputy: {
    type: Object as PropType<Deputy>,
    required: true
  },
  mandates: {
    type: Object as PropType<Mandate[]>,
    required: true
  }
});

function getLatestPoliticalGroup(mandates: Mandate[]) {
  const mandatesGP = _.groupBy(mandates, (mandate) => mandate.authority.authorityType)["GP"]
  if (!_.isEmpty(mandatesGP)) {
    return _.last(_.sortBy(mandatesGP, (mandate) => mandate.startDate))!.authority
  }
  else {
    return {
      id: "",
      authorityType: "GP",
      label: "Parti inconnu",
      labelAbbreviated: "PI",
      associatedColor: "--color-gray-500"

    }
  }
}

</script>

<template>
  <div class="flex flex-col gap-3 w-2xs p-3 rounded-xl bg-white shadow-md outline outline-black/5">
    <div class="text-base text-gray-700">
      <p>{{ deputy.firstName }} {{ deputy.lastName }} {{ deputy.id }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i :class="['text-sm', 'pi', 'pi-building-columns']"
        :style="{ color: getLatestPoliticalGroup(mandates).associatedColor }"></i>
      <p class="text-sm text-gray-500 p-0">{{ getLatestPoliticalGroup(mandates).label }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i class="text-sm pi pi-map-marker text-orange-300"></i>
      <p class="text-sm text-gray-500">{{ "To fill groupe région" }}, {{ "To fill groupe département" }}</p>
    </div>
  </div>
</template>

import axios from 'axios'

const restBallots = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/ballots',
})

export default class BallotsService {
  public getBallots = async () => {
    try {
      return (await restBallots.get('')).data
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }

  public capitalizeFirstLetter(val: string) {
    return String(val).charAt(0).toUpperCase() + String(val).slice(1)
  }
}
